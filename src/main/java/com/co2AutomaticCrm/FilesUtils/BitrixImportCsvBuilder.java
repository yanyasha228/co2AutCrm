package com.co2AutomaticCrm.FilesUtils;

import java.io.IOException;

public interface BitrixImportCsvBuilder {
    //Should return the url in String of csv file
    String buildCsvFromNotImportedProducts() throws IOException;
}
