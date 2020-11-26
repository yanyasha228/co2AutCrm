package com.co2AutomaticCrm.Services;

import com.co2AutomaticCrm.Dao.ProductDao;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleEntitySaveUpdateException;
import com.co2AutomaticCrm.Models.Group;
import com.co2AutomaticCrm.Models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService {

//    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> findAllByGroup(Group group) {
        return productDao.findAllByGroup(group);
    }

    @Override
    public List<Product> findAllByAvailability(Boolean availability) {
        return productDao.findAllByAvailability(availability);
    }

    @Override
    public List<Product> findAllByGroupAndAvailability(Group group, Boolean availability) {
        return productDao.findAllByGroupAndAvailability(group, availability);
    }

    @Override
    public Product save(Product product) throws ImpossibleEntitySaveUpdateException {

        if (product != null)
            if (product.getId() <= 0)
                throw new ImpossibleEntitySaveUpdateException("Attempt to update entity without ID!!!");

        return productDao.save(product);

    }

    @Override
    public List<Product> save(List<Product> productList) {

        List<Product> productListForSave = productList.stream().filter(product -> {
            if (product.getId() <= 0) {
//                log.warn("Attempt to update entity without ID!!!");
                return false;
            }
            return true;

        }).collect(Collectors.toList());


        return productDao.saveAll(productListForSave);
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productDao.findById(id);
    }

    @Override
    public Optional<Product> findByBitrixId(Long id) {
        return productDao.findByBitrixId(id);
    }

    @Override
    public Optional<Product> findProductByName(String name) {
        return productDao.findProductByName(name);
    }

    @Override
    public List<Product> findProductsByNonFullProductNameRegardlessOfTheWordsOrder(String nonFullProductName) {

        String[] searchingWords = nonFullProductName.split("\\s");

        List<Product> firstWordSearchFromDb;

        List<Product> productsThatMatch = new ArrayList<>();

        if (searchingWords.length != 0 && !searchingWords[0].equalsIgnoreCase("")) {
            firstWordSearchFromDb = productDao.findProductsByNameIgnoreCaseContaining(searchingWords[0]);
        } else return productsThatMatch;


        if (firstWordSearchFromDb.size() != 0) {
            out:
            for (Product prodForSearch : firstWordSearchFromDb) {
                for (int i = 1; i < searchingWords.length; i++) {
                    if (!prodForSearch.getName().toLowerCase().contains(searchingWords[i].toLowerCase())) continue out;
                }
                productsThatMatch.add(prodForSearch);
            }
        }


        return productsThatMatch;
    }

    @Override
    public List<Product> findProductsByNameIgnoreCaseContaining(String nonFullName) {
        return productDao.findProductsByNameIgnoreCaseContaining(nonFullName);
    }

    @Override
    public Page<Product> findAllWithPagination(Pageable pageable) {

        return productDao.findAll(pageable);
    }

    @Override
    public Page<Product> findProductsByNameIgnoreCaseContainingWithPagination(String nonFullName, Pageable pageable) {
        return productDao.findProductsByNameIgnoreCaseContaining(nonFullName,
                pageable);
    }

    @Override
    public Page<Product> findProductsByAvailabilityWithPagination(boolean availability, Pageable pageable) {
        return productDao.findProductsByAvailability(availability,
                pageable);
    }

    @Override
    public Page<Product> findProductsByGroupWithPagination(Group group, Pageable pageable) {

        return productDao.findProductsByGroup(group,
                pageable);
    }

    @Override
    public Page<Product> findProductsByGroupAndAvailabilityWithPagination(Group group, boolean availability, Pageable pageable) {

        return productDao.findProductsByGroupAndAvailability(group,
                availability,
                pageable);
    }

    @Override
    public Page<Product> findProductsByNameIgnoreCaseContainingAndGroupWithPagination(String nonFullName, Group group, Pageable pageable) {
        return productDao.findProductsByNameIgnoreCaseContainingAndGroup(nonFullName,
                group,
                pageable);
    }

    @Override
    public Page<Product> findProductsByNameIgnoreCaseContainingAndAvailabilityWithPagination(String nonFullName, boolean availability, Pageable pageable) {

        return productDao.findProductsByNameIgnoreCaseContainingAndAvailability(nonFullName,
                availability,
                pageable);
    }

    @Override
    public Page<Product> findProductsByNameIgnoreCaseContainingAndGroupAndAvailabilityWithPagination(String nonFullName, Group group, boolean availability, Pageable pageable) {
        return productDao.findProductsByNameIgnoreCaseContainingAndGroupAndAvailability(nonFullName,
                group,
                availability,
                pageable);
    }

    @Override
    public Page<Product> findProductsWithPagination(String nonFullProductName, Group group, Boolean availability, Pageable pageable) {

        if (nonFullProductName.replaceAll(" ", "").isEmpty()) {

            if (availability == null && group != null) {

                return findProductsByGroupWithPagination(group, pageable);
            }

            if (availability != null && group == null) {

                return findProductsByAvailabilityWithPagination(availability, pageable);
            }
            if (availability != null && group != null) {
                return findProductsByGroupAndAvailabilityWithPagination(group,
                        availability,
                        pageable);
            }
        } else {

            if (availability == null && group != null) {

                return findProductsByNameIgnoreCaseContainingAndGroupWithPagination(nonFullProductName, group, pageable);
            }

            if (availability != null && group == null) {

                return findProductsByNameIgnoreCaseContainingAndAvailabilityWithPagination(nonFullProductName,
                        availability,
                        pageable);
            }
            if (availability != null && group != null) {
                return findProductsByNameIgnoreCaseContainingAndGroupAndAvailabilityWithPagination(nonFullProductName,
                        group,
                        availability,
                        pageable);
            }

            return findProductsByNameIgnoreCaseContainingWithPagination(nonFullProductName, pageable);
        }

        return findAllWithPagination(pageable);
    }
}
