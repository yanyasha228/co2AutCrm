package com.co2AutomaticCrm.RestDao.RestBitrixDao;

import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.ContactRestBitrixDto;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests.AddContactBitrixRestRequest;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests.GetContactBitrixRestRequest;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests.GetContactListBitrixWithPaginationRequest;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Responses.AddContactBitrixRestResponse;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Responses.GetContactBitrixRestResponse;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Responses.GetContactListBitrixWithPaginationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class ContactRestBitrixDaoImpl implements ContactRestBitrixDao {


    @Value("${rest.bitrix.api.token}")
    private String apiToken;

    @Value("${rest.bitrix.api.post.contact.contactBitrixId.getContact}")
    private String getContactByIdUri;

    @Value("${rest.bitrix.api.post.contact.getContactListWithPagination}")
    private String getContactListWithPaginationUrl;

    @Value("${rest.bitrix.api.post.contact.contactRestBitrixDto.add}")
    private String addContactUri;

    @Autowired
    private BitrixRestApiExchanger bitrixRestApiExchanger;

    @Override
    public Optional<ContactRestBitrixDto> get(Integer id) {
        if (id == 0) return Optional.empty();


        ResponseEntity<GetContactBitrixRestResponse> getContactByIdEntity = bitrixRestApiExchanger.exchange(String.format(getContactByIdUri, apiToken), HttpMethod.POST, new GetContactBitrixRestRequest(id), GetContactBitrixRestResponse.class);

        System.out.println("getByContactId STATUS_CODE: " + getContactByIdEntity.getStatusCode());

        if (getContactByIdEntity.getStatusCode() == HttpStatus.OK) {

            return Optional.of(getContactByIdEntity.getBody().getContactRestBitrixDto());

        }
        return Optional.empty();

    }

    @Override
    public List<ContactRestBitrixDto> getAll() {

        List<ContactRestBitrixDto> contactRestBitrixDtos = new ArrayList<>();

        ResponseEntity<GetContactListBitrixWithPaginationResponse> contactListResponseEntity = bitrixRestApiExchanger.exchange(String.format(getContactListWithPaginationUrl, apiToken), HttpMethod.POST, new GetContactListBitrixWithPaginationRequest(0), GetContactListBitrixWithPaginationResponse.class);


        if (contactListResponseEntity.getStatusCode() == HttpStatus.OK) {

            boolean next = false;

            int start = contactListResponseEntity.getBody().getNext();

            contactRestBitrixDtos.addAll(contactListResponseEntity.getBody().getContactDtos());

            if (contactListResponseEntity.getBody().getNext() > 0) next = true;

            while (next) {

                ResponseEntity<GetContactListBitrixWithPaginationResponse> contactListResponseEntitySec = bitrixRestApiExchanger.exchange(String.format(getContactListWithPaginationUrl, apiToken), HttpMethod.POST, new GetContactListBitrixWithPaginationRequest(start), GetContactListBitrixWithPaginationResponse.class);

                if (contactListResponseEntitySec.getStatusCode() == HttpStatus.OK) {

                    contactRestBitrixDtos.addAll(contactListResponseEntitySec.getBody().getContactDtos());
                    start = contactListResponseEntitySec.getBody().getNext();
                    next = contactListResponseEntitySec.getBody().getNext() > 0;

                } else next = false;

            }

        }

        return contactRestBitrixDtos;
    }


    @Override
    public Optional<ContactRestBitrixDto> add(ContactRestBitrixDto contactRestBitrixDto) {

        if (Objects.isNull(contactRestBitrixDto)) return Optional.empty();

        ResponseEntity<AddContactBitrixRestResponse> addContactEntity = bitrixRestApiExchanger.exchange(String.format(addContactUri, apiToken), HttpMethod.POST, new AddContactBitrixRestRequest(contactRestBitrixDto), AddContactBitrixRestResponse.class);

        System.out.println("AddContact STATUS_CODE: " + addContactEntity.getStatusCode());
        if (addContactEntity.getStatusCode() == HttpStatus.OK) {

            return Optional.of(contactRestBitrixDto);

        }

        return Optional.empty();

    }
}



