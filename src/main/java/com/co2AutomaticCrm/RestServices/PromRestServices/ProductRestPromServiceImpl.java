package com.co2AutomaticCrm.RestServices.PromRestServices;


import com.co2AutomaticCrm.HelpUtils.Mappers.ModelMappers.Mapper;
import com.co2AutomaticCrm.Models.Dto.RestDto.RestPromDto.ProductRestPromDto;
import com.co2AutomaticCrm.Models.Product;
import com.co2AutomaticCrm.RestDao.RestPromDao.ProductRestPromDao;
import com.co2AutomaticCrm.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductRestPromServiceImpl implements ProductRestPromService {

    @Autowired
    private ProductRestPromDao productRestDao;

    @Autowired
    private ProductService productService;

    @Autowired
    private Mapper<Product, ProductRestPromDto> productMapper;

    @Override
    public Optional<Product> getProductById(Long id) {
        Product product = productMapper.toEntity(productRestDao.getProductById(id).orElse(null));
        return Optional.ofNullable(product);
    }

    @Override
    public List<Product> getAll() throws InterruptedException {

        List<Product> prodListFromDb = productService.findAll();
        List<Product> productList = new ArrayList<>();


        for (Product pr : prodListFromDb) {


            Optional<ProductRestPromDto> prodToAdd = productRestDao.getProductById(pr.getId());

            prodToAdd.ifPresent(productDto -> productList.add(productMapper.toEntity(productDto)));

            Thread.sleep(70);

        }

        return productList;
    }

    @Override
    public List<Product> getProductsByGroupId(int id) {
        return productRestDao.
                getProductsByGroupId(id).stream().
                map(productDto -> productMapper.toEntity(productDto)).collect(Collectors.toList());
    }

    @Override
    public List<Product> postProducts(List<Product> productList) {

        return productRestDao.postProducts(productList.stream().
                map(product -> productMapper.toDto(product))
                .collect(Collectors.toList())).stream().
                map(productDto -> productMapper.toEntity(productDto)).collect(Collectors.toList());
    }
}

