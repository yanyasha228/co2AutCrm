package com.co2AutomaticCrm.Dao.ManDaos;

import com.co2AutomaticCrm.Models.HelpModels.GenProductManipulation;
import com.co2AutomaticCrm.Models.ModelEnums.ProductManipulationType;
import com.co2AutomaticCrm.Models.ProductLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class ProductManipulationRepExtensionImpl implements ProductManipulationRepExtension {

    @Autowired
    private EntityManager em;

    @Override
    public List<GenProductManipulation> findAllByProductId(Long id) {

        List<GenProductManipulation> resList = em.createNativeQuery("select * from product_manipulations where product_manipulations.id in ( select manipulation_id from product_lines where product_id = :id) order by creation_date desc "
                , GenProductManipulation.class)
                .setParameter("id", id).getResultList();

        generateManipulationResultStrings(resList, id);
        setManipulationMomentAmount(resList,id);

        return resList;
    }

    @Override
    public Page<GenProductManipulation> findAllByProductIdWithPagination(Long id, Pageable pageable) {

        List<GenProductManipulation> resList = em.createNativeQuery("select * from product_manipulations where product_manipulations.id in ( select manipulation_id from product_lines where product_id = :id) order by creation_date desc"
                , GenProductManipulation.class)
                .setParameter("id", id)
                .setMaxResults(pageable.getPageSize())
                .setFirstResult(pageable.getPageSize() * pageable.getPageNumber())
                .getResultList();

        generateManipulationResultStrings(resList, id);
        setManipulationMomentAmount(resList,id);
        Query countQuery = em.createNativeQuery("select count(*) from product_manipulations where product_manipulations.id in ( select manipulation_id from product_lines where product_id = :id)")
                .setParameter("id", id);
        Long total = ((Number) countQuery.getSingleResult()).longValue();

        Page<GenProductManipulation> pageToRet = new PageImpl<GenProductManipulation>(resList, pageable, total);

        return pageToRet;
    }

    @Override
    public Page<GenProductManipulation> findAllByProductIdAndTypeWithPagination(Long id, ProductManipulationType productManipulationType, Pageable pageable) {

        List<GenProductManipulation> resList = em.createNativeQuery("select * from product_manipulations where product_manipulations.id in ( select manipulation_id from product_lines where product_id = :id) AND product_manipulations.type = :type order by creation_date desc"
                , GenProductManipulation.class)
                .setParameter("id", id)
                .setParameter("type", productManipulationType.name())
                .setMaxResults(pageable.getPageSize())
                .setFirstResult(pageable.getPageSize() * pageable.getPageNumber())
                .getResultList();

        generateManipulationResultStrings(resList, id);
        setManipulationMomentAmount(resList,id);

        Query countQuery = em.createNativeQuery("select count(*) from product_manipulations where product_manipulations.id in ( select manipulation_id from product_lines where product_id = :id) AND product_manipulations.type = :type")
                .setParameter("id", id)
                .setParameter("type", productManipulationType.name());
        Long total = ((Number) countQuery.getSingleResult()).longValue();

        Page<GenProductManipulation> pageToRet = new PageImpl<GenProductManipulation>(resList, pageable, total);

        return pageToRet;

    }


    private void generateManipulationResultStrings(List<GenProductManipulation> manList, Long prodId) {

        if (Objects.isNull(manList) || manList.isEmpty()) return;

        manList = manList.stream().map(genProductManipulation -> {

            List<ProductLine> productLinesThatMatch = genProductManipulation
                    .getProductLines()
                    .stream().filter(productLine -> productLine.getProduct().getId() == prodId)
                    .collect(Collectors.toList());

            int prodAmmount = 0;

            for (ProductLine pr :
                    productLinesThatMatch) {
                prodAmmount += pr.getAmount();
            }

            String resString = "";

            switch (genProductManipulation.getType()) {
                case DEMAND:
                    resString = "-" + prodAmmount;
                    break;
                case SUPPLY:
                    resString = "+" + prodAmmount;
                    break;
            }

            genProductManipulation.setManipulationResultStringByProduct(resString);

            return genProductManipulation;

        }).collect(Collectors.toList());


    }

    private void setManipulationMomentAmount(List<GenProductManipulation> manList , Long prodId){
        manList = manList.stream().map(genProductManipulation -> {

            ProductLine productLineThatMatch = genProductManipulation
                    .getProductLines()
                    .stream().filter(productLine -> productLine.getProduct().getId() == prodId).findFirst().get();

            genProductManipulation.setManipulationMomentAmount(productLineThatMatch.getManipulationMomentAmount());

            return genProductManipulation;

        }).collect(Collectors.toList());
    }


}
