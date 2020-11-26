package com.co2AutomaticCrm.Handlers.BitrixFlowHandlers;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.EntityInconsistencyException;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleRestUpdateEntityException;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.InsufficientProductAmountException;
import com.co2AutomaticCrm.HelpUtils.Mappers.ModelMappers.BitrixModelToLocalModelMappers.BitrixProductRowToProductLineMapper;
import com.co2AutomaticCrm.Models.BitrixDealDemand;
import com.co2AutomaticCrm.Models.BitrixModels.BitrixDeal;
import com.co2AutomaticCrm.Models.BitrixModels.BitrixProductRow;
import com.co2AutomaticCrm.Models.ProductLine;
import com.co2AutomaticCrm.Services.DealDemandService;
import com.co2AutomaticCrm.Services.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class StoreKeepHandlerImpl implements StoreKeepHandler {

    @Autowired
    private DealDemandService dealDemandService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private BitrixProductRowToProductLineMapper mapper;

    @Override
    public void handle(BitrixDeal bitrixDeal) throws InsufficientProductAmountException, EntityInconsistencyException, ImpossibleRestUpdateEntityException {

        Optional<BitrixDealDemand> dbDealDemand = dealDemandService.findByBitrixDealId(bitrixDeal.getId());

        BitrixDealDemand dealDemand = new BitrixDealDemand();

        dealDemand.setUser(workerService.findById(bitrixDeal.getManager().getId()).orElse(null));

        if (!bitrixDeal.isStoreProcCalcFlag() && !dbDealDemand.isPresent()) {
            //if not in delete stage
            if (!bitrixDeal.getStage().equalsIgnoreCase(BitrixDealFlowHandlerImpl.DELETE_STAGE)) {

                dealDemand.setBitrixDealId(bitrixDeal.getId());
                dealDemand.setProductLines(mapBitrixProductRowsToProductLine(bitrixDeal.getProductRows()));
                dealDemandService.save(dealDemand);
                bitrixDeal.setStoreProcCalcFlag(true);
            }


        } else if (bitrixDeal.isStoreProcCalcFlag() && dbDealDemand.isPresent()) {
            //if in DELETE_STAGE delete product demand from database and turn off flag store_proc_calc_flag
            if (!bitrixDeal.getStage().equalsIgnoreCase(BitrixDealFlowHandlerImpl.DELETE_STAGE)) {

                if (!checkProductLinesDemandConsistency(bitrixDeal, dbDealDemand.get())) {
                    dealDemandService.deleteByBitrixDealId(bitrixDeal.getId());
                    bitrixDeal.setStoreProcCalcFlag(false);

                    dealDemand.setBitrixDealId(bitrixDeal.getId());
                    dealDemand.setProductLines(mapBitrixProductRowsToProductLine(bitrixDeal.getProductRows()));
                    dealDemandService.save(dealDemand);
                    bitrixDeal.setStoreProcCalcFlag(true);
                }

            } else {
                dealDemandService.deleteByBitrixDealId(bitrixDeal.getId());
                bitrixDeal.setStoreProcCalcFlag(false);
            }

        } else if (!bitrixDeal.isStoreProcCalcFlag() && dbDealDemand.isPresent()) {
            dealDemandService.deleteByBitrixDealId(bitrixDeal.getId());

        } else if (bitrixDeal.isStoreProcCalcFlag() && !dbDealDemand.isPresent()) {
            bitrixDeal.setStoreProcCalcFlag(false);
            dealDemand.setBitrixDealId(bitrixDeal.getId());
            dealDemand.setProductLines(mapBitrixProductRowsToProductLine(bitrixDeal.getProductRows()));
            dealDemandService.save(dealDemand);
            bitrixDeal.setStoreProcCalcFlag(true);
        }

    }

    private boolean checkProductLinesDemandConsistency(BitrixDeal bitrixDeal, BitrixDealDemand dealDemand) {

        if (!(bitrixDeal.getProductRows().size() == dealDemand.getProductLines().size())) {

            return false;
        }
///////1111111
        for (BitrixProductRow bitrixProductRow :
                bitrixDeal.getProductRows()) {

            for (ProductLine productLine :
                    dealDemand.getProductLines()) {
                if (!(productLine.getProduct().getBitrixId() == bitrixProductRow.getProductBitrixId()) ||
                        !(productLine.getAmount() == bitrixProductRow.getQuantity())) {
                    return false;
                }
            }
        }

        return true;
    }

    private List<ProductLine> mapBitrixProductRowsToProductLine(List<BitrixProductRow> productRows) throws EntityInconsistencyException {
        List<ProductLine> list = new ArrayList<>();
        for (BitrixProductRow pr : productRows) {
            ProductLine productLine = mapper.toProductLine(pr);
            list.add(productLine);
        }
        return list;
    }


}
