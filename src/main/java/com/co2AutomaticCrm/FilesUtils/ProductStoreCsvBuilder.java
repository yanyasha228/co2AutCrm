package com.co2AutomaticCrm.FilesUtils;


import com.co2AutomaticCrm.Models.Group;

import java.io.IOException;

public interface ProductStoreCsvBuilder {

    String createCsv(Boolean availability) throws IOException;

    String createCsv(Group group, Boolean availability) throws IOException;
}
