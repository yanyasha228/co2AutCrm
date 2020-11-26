package com.co2AutomaticCrm;

import com.co2AutomaticCrm.Dao.SalaryDecreaseDao;
import com.co2AutomaticCrm.Handlers.HandlersUtils.WorkerDailySalaryCounter;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleRestUpdateEntityException;
import com.co2AutomaticCrm.HelpUtils.Mappers.ModelMappers.Mapper;
import com.co2AutomaticCrm.Models.*;
import com.co2AutomaticCrm.Models.BitrixModels.BitrixNotify;
import com.co2AutomaticCrm.Models.BitrixModels.BitrixUser;
import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.ContactRestBitrixDto;
import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.UserRestBitrixDto;
import com.co2AutomaticCrm.RestDao.RestBitrixDao.*;
import com.co2AutomaticCrm.RestServices.BitrixRestServices.NotifyRestBitrixService;
import com.co2AutomaticCrm.Services.DailySalaryService;
import com.co2AutomaticCrm.Services.SalaryIncreaseService;
import com.co2AutomaticCrm.Services.WorkerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestBrest {

    @Autowired
    private NotifyRestBitrixDao notifyRestBitrixDao;

    @Autowired
    private NotifyRestBitrixService notifyRestBitrixService;

    @Autowired
    private SalaryDecreaseDao<ManagerSalaryDecrease> salaryDecreaseDao;


    @Autowired
    private SalaryIncreaseService<ManagerSalaryIncrease> salaryIncreaseService;

    @Autowired
    private DailySalaryService<ManagerDailySalary> dailySalaryDailySalaryService;

    @Autowired
    @Qualifier("managerDailySalaryCounter")
    private WorkerDailySalaryCounter workerDailySalaryCounter;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private ContactRestBitrixDao contactRestBitrixDao;

    @Autowired
    private ProductRestBitrixDao productRestBitrixDao;

    @Autowired
    private UserRestBitrixDao userRestBitrixDao;

    @Autowired
    private Mapper<BitrixUser , UserRestBitrixDto> mapper;

    @Test
    public void sendMess(){
notifyRestBitrixService.sentNotify(mapper.toEntity(userRestBitrixDao.get(1L).get()), "DI");
//        notifyRestBitrixDao.post(new BitrixNotify(mapper.toEntity(userRestBitrixDao.get(1L).get()),"DI"));
    }

    //    @Test
//    public void test2(){
//
//        LocalDate localDate = LocalDate.now();
//        LocalDate dayBefore = LocalDate.parse("2019-11-03");
//        List<SalaryDecrease> salaryDecreasesAll = (salaryDecreaseDao.findAll());
//        List<SalaryDecrease> salaryDecreasesByLocDate = salaryDecreaseDao.findByCreationDate(localDate);
//
//        int op =1;
//    }
//    @Before
//    public void upTestData() {
//
//        List<ManagerSalaryIncrease> list = new ArrayList<>();
//
//        Manager manager = managerService.findById(1L).get();
//        Manager manager1 = managerService.findById(6L).get();
//        Manager manager2 = managerService.findById(8L).get();
//
//        ManagerSalaryIncrease salaryIncrease = new ManagerSalaryIncrease();
//        salaryIncrease.setWorker(manager);
//        salaryIncrease.setBitrixDealId(678);
//        salaryIncrease.setSalaryIncreaseAmount(57);
//        list.add(salaryIncrease);
//
//        ManagerSalaryIncrease salaryIncrease1 = new ManagerSalaryIncrease();
//        salaryIncrease1.setWorker(manager);
//        salaryIncrease1.setBitrixDealId(680);
//        salaryIncrease1.setSalaryIncreaseAmount(68);
//        list.add(salaryIncrease1);
//
//        ManagerSalaryIncrease salaryIncrease2 = new ManagerSalaryIncrease();
//        salaryIncrease2.setWorker(manager);
//        salaryIncrease2.setBitrixDealId(568);
//        salaryIncrease2.setSalaryIncreaseAmount(234);
//        list.add(salaryIncrease2);
//
//        ManagerSalaryIncrease salaryIncrease3 = new ManagerSalaryIncrease();
//        salaryIncrease3.setWorker(manager1);
//        salaryIncrease3.setBitrixDealId(673);
//        salaryIncrease3.setSalaryIncreaseAmount(10);
//        list.add(salaryIncrease3);
//
//        ManagerSalaryIncrease salaryIncrease4 = new ManagerSalaryIncrease();
//        salaryIncrease4.setWorker(manager1);
//        salaryIncrease4.setBitrixDealId(679);
//        salaryIncrease4.setSalaryIncreaseAmount(5);
//        list.add(salaryIncrease4);
//
//        ManagerSalaryIncrease salaryIncrease5 = new ManagerSalaryIncrease();
//        salaryIncrease5.setWorker(manager1);
//        salaryIncrease5.setBitrixDealId(623);
//        salaryIncrease5.setSalaryIncreaseAmount(327);
//        list.add(salaryIncrease5);
//
//        ManagerSalaryIncrease salaryIncrease6 = new ManagerSalaryIncrease();
//        salaryIncrease6.setWorker(manager2);
//        salaryIncrease6.setBitrixDealId(611);
//        salaryIncrease6.setSalaryIncreaseAmount(87);
//        list.add(salaryIncrease6);
//
//        ManagerSalaryIncrease salaryIncrease7 = new ManagerSalaryIncrease();
//        salaryIncrease7.setWorker(manager2);
//        salaryIncrease7.setBitrixDealId(378);
//        salaryIncrease7.setSalaryIncreaseAmount(27);
//        list.add(salaryIncrease7);
//
//        ManagerSalaryIncrease salaryIncrease8 = new ManagerSalaryIncrease();
//        salaryIncrease8.setWorker(manager2);
//        salaryIncrease8.setBitrixDealId(278);
//        salaryIncrease8.setSalaryIncreaseAmount(17);
//        list.add(salaryIncrease8);
//
//        for (ManagerSalaryIncrease mnIn : list) {
//            salaryIncreaseService.save(mnIn);
//        }
//
//
//    }

    @Test
    @Transactional
    public void test() throws ImpossibleRestUpdateEntityException, InterruptedException {

        ContactRestBitrixDto contactRestBitrixDto = contactRestBitrixDao.get(700).orElse(null);

        int i = 0;
//        List<ContactRestBitrixDto> contactRestBitrixDaoAll = contactRestBitrixDao.getAll();
//
//
//        for (ContactRestBitrixDto cont :
//                contactRestBitrixDaoAll) {
//
////            Thread.sleep(200);
//            System.out.println("-------------------------------------------------------------------------------");
//            System.out.println(LocalDateTime.now());
//
//            System.out.println("ContactId : " + cont.getId());
//
//            contactRestBitrixDao.get(cont.getId());
//
//        }

    }

    @Test
    public void searchUserByEmailTest(){
        UserRestBitrixDto userRestBitrixDto =   userRestBitrixDao.getByEmail("co2shopm21@gmail.com").orElse(null);

        int i = 0 ;

    }


}
