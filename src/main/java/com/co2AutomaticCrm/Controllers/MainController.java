package com.co2AutomaticCrm.Controllers;


import com.co2AutomaticCrm.FilesUtils.BitrixImportCsvBuilderImpl;
import com.co2AutomaticCrm.FilesUtils.ProductStoreCsvBuilder;
import com.co2AutomaticCrm.FilesUtils.ProductStoreCsvCorrectionsUploader;
import com.co2AutomaticCrm.Handlers.HandlersUtils.WorkerDailySalaryCounter;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.*;
import com.co2AutomaticCrm.Models.Group;
import com.co2AutomaticCrm.Models.User;
import com.co2AutomaticCrm.Services.GroupService;
import com.co2AutomaticCrm.SyncUtils.EntityConnectors.BitrixProductEntityConnector;
import com.co2AutomaticCrm.SyncUtils.*;
import com.co2AutomaticCrm.TemporalUtils.BitrixDealDemandUserTier;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private BitrixDealDemandUserTier bitrixDealDemandUserTier;

    @Autowired
    private BitrixImportCsvBuilderImpl bitrixImportCsvBuilder;

    @Autowired
    private RozetkaCatalogXmlSynchronizer rozetkaCatalogXmlSynchronizer;

    @Autowired
    private ProductStoreCsvBuilder storeCsvBuilder;

    @Autowired
    private ProductsPromRestSynchronizer productsPromRestSynchronizer;

    @Autowired
    private BitrixProductEntityConnector bitrixProductEntityConnector;

    @Autowired
    private ProductsBitrixRestSynchronizer productsBitrixRestSynchronizer;

    @Autowired
    private ProductsPromXmlSynchronizer productsPromXmlSynchronizer;

    @Autowired
    private WholeSalePricesCsvImporter wholeSalePricesCsvImporter;

    @Autowired
    private ProductStoreCsvCorrectionsUploader productStoreCsvCorrectionsUploader;

    @Autowired
    @Qualifier("managerDailySalaryCounter")
    private WorkerDailySalaryCounter workerDailySalaryCounter;

    @Autowired
    private GroupService groupService;

    @Value("${upload.csvWholeSalePriceList.path}")
    private String fileUrlWithWholeSalePriceListCsv;

    @Value("${upload.csvStoreForCorrections.path}")
    private String csvFileForCorrectionsPath;

    @RequestMapping
    public String mainPage(Model model) {

        return "redirect:/products";

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("syncRozetkaCatalog")
    public String syncAndSaveRozetkaXmlCatalog() throws ProductXmlCatalogInaccessibilityException {
        rozetkaCatalogXmlSynchronizer.generateAndSave();
        return "redirect:/products";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("syncPrices")
    public String importWholeSalePrices(@RequestParam(required = false) String fileUrl) {

        if (!Objects.isNull(fileUrl)) {
            wholeSalePricesCsvImporter.importPrices(fileUrl);
            return "redirect:/products";
        }
        wholeSalePricesCsvImporter.importPrices(fileUrlWithWholeSalePriceListCsv);
        return "redirect:/products";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("bitrix/sync")
    public String bitrixSync() throws ImpossibleRestUpdateEntityException {

        productsBitrixRestSynchronizer.synchronizeAll();

        return "redirect:/products";

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("salary/manager/count")
    public String countManagersDailySalary() {
        workerDailySalaryCounter.count();
        return "redirect:/salary/manager/daily";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("bitrix/connect")
    public String connect() {

        bitrixProductEntityConnector.tieUnconnectedProductEntities();

        return "redirect:/products";

    }

    @GetMapping(value = "/importFileCsv.csv",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    byte[] importFile() throws IOException {
        InputStream in = new FileInputStream(bitrixImportCsvBuilder.buildCsvFromNotImportedProducts());
        return IOUtils.toByteArray(in);
    }

    @GetMapping(value = "/store.csv",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    byte[] storeCsvFile(@RequestParam(required = false) Integer groupId, @RequestParam(required = false) Boolean availability) throws IOException {
        InputStream in;
        if (!Objects.isNull(groupId) && groupId >= 0) {
            Optional<Group> groupOpt = groupService.findById(groupId);
            if (groupOpt.isPresent()) {
                in = new FileInputStream(storeCsvBuilder.createCsv(groupOpt.get(), availability));
            } else in = new FileInputStream(storeCsvBuilder.createCsv(availability));
        } else {
            in = new FileInputStream(storeCsvBuilder.createCsv(availability));
        }

        return IOUtils.toByteArray(in);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("prom/sync")
    public String promSync() throws InterruptedException {

        productsPromRestSynchronizer.synchronizeAll();

        return "redirect:/products";

    }

    @GetMapping("/tieBDDID")
    public String tieBitrixDealIdsForDemands() {

        bitrixDealDemandUserTier.tie();

        return "redirect:/products";

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("prom/sync/xml")
    public String promXmlSync(@RequestParam String syncUrl) {
        productsPromXmlSynchronizer.syncProductsWithPromXml(syncUrl);

        return "redirect:/products";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }


    @PostMapping("/uploadStoreCsv")
    public String singleFileUpload(Model model,
                                   @RequestParam("file") MultipartFile file,
                                   @AuthenticationPrincipal User user) throws IOException,
            ImpossibleEntitySaveUpdateException,
            ImpossibleAmountDecreasingException,
            ImpossibleRestUpdateEntityException,
            IncorrectFileException,
            CsvCorrectionResultException {

        if (file.isEmpty()) {
            model.addAttribute("error", "Ошибка : " + "Файл пустой");
            return "error";
        }

        //Get the file and save it somewhere
        byte[] bytes = file.getBytes();
        Path path = Paths.get(csvFileForCorrectionsPath);
        Files.write(path, bytes);

        productStoreCsvCorrectionsUploader.doCorrections(csvFileForCorrectionsPath, user);

        return "redirect:/products";
    }


    @ExceptionHandler(value = {ImpossibleEntitySaveUpdateException.class,
            ImpossibleAmountDecreasingException.class,
            InsufficientProductAmountException.class,
            IncorrectFileException.class,
            CsvCorrectionResultException.class,
            IOException.class})
    public String handleOrderManipulationException(Model model, Exception ex) {
//        logger.error(ex.toString());
        String message;
        if (ex instanceof CsvCorrectionResultException) {
            CsvCorrectionResultException csvEx = (CsvCorrectionResultException) ex;
            String errMessage = "";
            errMessage = errMessage.concat(csvEx.getMessage() + " :" + "<br>");
            for (CsvProductCorrectionException exp : csvEx.getExceptions()) {
                String exLine = "-------------------------------------------------------------------------------------------------------------------------------------------------------------------<br>";
                exLine = exLine.concat("--" + exp.getMessage()+ " :" + "<br>" + "----");
                exLine =  exLine.concat(exp.getException().getMessage() + " :" + "<br>" + "------");
                exLine = exLine.concat("Товар : " + exp.getProduct().getName() + " | " + "Кол-во : " + exp.getProduct().getAmount() + "<br>");
                errMessage = errMessage.concat(exLine);
            }
            message = errMessage;
        } else message = ex.getMessage();
        model.addAttribute("error",  message);

        return "error";
    }


}
