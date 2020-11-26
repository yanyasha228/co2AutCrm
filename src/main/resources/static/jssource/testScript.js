var arrLit = [];
arrLit.push("A");
arrLit.push("B");
arrLit.push("C");
arrLit.push("D");
arrLit.push("E");
arrLit.push("F");
arrLit.push("G");
arrLit.push("H");
arrLit.push("I");
arrLit.push("J");
arrLit.push("K");
arrLit.push("L");
arrLit.push("M");
arrLit.push("N");
arrLit.push("O");
arrLit.push("P");
arrLit.push("Q");
arrLit.push("R");
arrLit.push("S");
arrLit.push("T");
arrLit.push("U");
arrLit.push("V");
arrLit.push("W");
arrLit.push("X");

var beginRowIndexes = {

    "departurePointRowIndex" : 2,

    "placeOfArrivalRowIndex": 2,


    "phoneNumberRowIndex" : 3,


    "fullNameRowIndex" : 5,


    "paymentOptionRowIndex" : 4,


    "priceRowIndex" : 6

};

function instantiatePassengers(){
    var passengerArray = [];
    const distanceBetweenIdentRows = 9;

    const distanceBetweenIdentColumns = 4;
    const beginColumnIndex=3;
    const beginRowSheetIndex = 2;
    const posiblePassengersOnThePlaceAmmount = 5;
    const posiblePlacesAmount = 60;
    const rowsAmmountInPassengerForReading = 5;


    for(var j = 0 ; j<=posiblePlacesAmount-1 ; j++){

        for( var i = 0; i<=posiblePassengersOnThePlaceAmmount-1 ; i++){

            var passenger = new Passenger();



            var columnIndex = arrLit[beginColumnIndex + (distanceBetweenIdentColumns * i)];
            var specialColumnIndexForPlaceOfArrival = arrLit[(arrLit.indexOf(columnIndex) + 2)];

            passenger.setDeparturePointLink( columnIndex + (beginRowIndexes.departurePointRowIndex + (distanceBetweenIdentRows * j))) ;

//    arrivalPoint Setting with bias colum index 2  

            passenger.setPlaceOfArrivalLink(specialColumnIndexForPlaceOfArrival + (beginRowIndexes.placeOfArrivalRowIndex + (distanceBetweenIdentRows * j)));
            //
            passenger.setPhoneNumberLink(columnIndex + (beginRowIndexes.phoneNumberRowIndex + (distanceBetweenIdentRows * j)));

            passenger.setFullNameLink(columnIndex + (beginRowIndexes.fullNameRowIndex + (distanceBetweenIdentRows * j)));

            passenger.setPaymentOptionLink(columnIndex + (beginRowIndexes.paymentOptionRowIndex + (distanceBetweenIdentRows * j)));

            passenger.setPriceLink(columnIndex + (beginRowIndexes.priceRowIndex + (distanceBetweenIdentRows * j)));

            passengerArray.push(passenger);


        }

    }

    return passengerArray;
}






function Place(passengers) {

    this.passengers = passengers;

    var shownPassenger;

}



function validatePassenger(spreadSheet , passenger){

    passenger.setDeparturePoint(spreadSheet.getRange(passenger.getDeparturePointLink()).getValue());
    passenger.setPlaceOfArrival(spreadSheet.getRange(passenger.getPlaceOfArrivalLink()).getValue());
    passenger.setPhoneNumber(spreadSheet.getRange(passenger.getPhoneNumberLink()).getValue());
    passenger.setFullName(spreadSheet.getRange(passenger.getFullNameLink()).getValue());
    passenger.setPaymentOption(spreadSheet.getRange(passenger.getPaymentOptionLink()).getValue());
    passenger.setPrice(spreadSheet.getRange(passenger.getPriceLink()).getValue());

}

function Place(departurePointLink , placeOfArrivalLink , phoneNumberLink , fullNameLink , paymentOptionLink , priceLink){

    this.departurePointLink = departurePointLink;
    this.departurePoint = "";

    this.placeOfArrivalLink = placeOfArrivalLink;
    this.placeOfArrival = "";

    this.phoneNumberLink = phoneNumberLink;
    this.phoneNumber = "";

    this.fullNameLink = fullNameLink;
    this.fullName="";

    this.paymentOptionLink = paymentOptionLink;
    this.paymentOption = "";

    this.priceLink = priceLink;
    this.price = "";

}
//Place getter and setters
Passenger.prototype.getDeparturePoint = function(){
    return this.departurePoint;
}
Passenger.prototype.getPlaceOfArrival = function(){
    return this.placeOfArrival;
}

Passenger.prototype.getPhoneNumber = function(){
    return this.phoneNumber;
}

Passenger.prototype.getFullName = function(){
    return this.fullName;
}

Passenger.prototype.getPaymentOption = function(){
    return this.paymentOption;
}

Passenger.prototype.getPrice = function(){
    return this.price;
}

Passenger.prototype.getDeparturePointLink = function(){
    return this.departurePointLink;
}
Passenger.prototype.getPlaceOfArrivalLink = function(){
    return this.placeOfArrivalLink;
}

Passenger.prototype.getPhoneNumberLink = function(){
    return this.phoneNumberLink;
}

Passenger.prototype.getFullNameLink = function(){
    return this.fullNameLink;
}

Passenger.prototype.getPaymentOptionLink = function(){
    return this.paymentOptionLink;
}

Passenger.prototype.getPriceLink = function(){
    return this.priceLink;
}
//Passenger setters

Place.prototype.setDeparturePoint = function(departurePoint){
    this.departurePoint = departurePoint;
}
Place.prototype.setPlaceOfArrival = function(placeOfArrival){
    this.placeOfArrival = placeOfArrival;
}

Place.prototype.setPhoneNumber = function(phoneNumber){
    this.phoneNumber = phoneNumber;
}

Place.prototype.setFullName = function(fullName){
    this.fullName = fullName;
}

Place.prototype.setPaymentOption = function(paymentOption){
    this.paymentOption = paymentOption;
}

Place.prototype.setPrice = function(price){
    this.price = price;
}

Place.prototype.setDeparturePointLink = function(departurePointLink){
    this.departurePointLink = departurePointLink;
}
Place.prototype.setPlaceOfArrivalLink = function(placeOfArrivalLink){
    this.placeOfArrivalLink = placeOfArrivalLink;
}

Place.prototype.setPhoneNumberLink = function(phoneNumberLink){
    this.phoneNumberLink = phoneNumberLink;
}

Place.prototype.setFullNameLink = function(fullNameLink){
    this.fullNameLink = fullNameLink;
}

Place.prototype.setPaymentOptionLink = function(paymentOptionLink){
    this.paymentOptionLink = paymentOptionLink;
}

Place.prototype.setPriceLink = function(priceLink){
    this.priceLink = priceLink;
}

function Passenger(departurePointLink , placeOfArrivalLink , phoneNumberLink , fullNameLink , paymentOptionLink , priceLink){

    this.departurePointLink = departurePointLink;
    this.departurePoint = "";

    this.placeOfArrivalLink = placeOfArrivalLink;
    this.placeOfArrival = "";

    this.phoneNumberLink = phoneNumberLink;
    this.phoneNumber = "";

    this.fullNameLink = fullNameLink;
    this.fullName="";

    this.paymentOptionLink = paymentOptionLink;
    this.paymentOption = "";

    this.priceLink = priceLink;
    this.price = "";

}

// Passenger getters
Passenger.prototype.getDeparturePoint = function(){
    return this.departurePoint;
}
Passenger.prototype.getPlaceOfArrival = function(){
    return this.placeOfArrival;
}

Passenger.prototype.getPhoneNumber = function(){
    return this.phoneNumber;
}

Passenger.prototype.getFullName = function(){
    return this.fullName;
}

Passenger.prototype.getPaymentOption = function(){
    return this.paymentOption;
}

Passenger.prototype.getPrice = function(){
    return this.price;
}

Passenger.prototype.getDeparturePointLink = function(){
    return this.departurePointLink;
}
Passenger.prototype.getPlaceOfArrivalLink = function(){
    return this.placeOfArrivalLink;
}

Passenger.prototype.getPhoneNumberLink = function(){
    return this.phoneNumberLink;
}

Passenger.prototype.getFullNameLink = function(){
    return this.fullNameLink;
}

Passenger.prototype.getPaymentOptionLink = function(){
    return this.paymentOptionLink;
}

Passenger.prototype.getPriceLink = function(){
    return this.priceLink;
}
//Passenger setters

Passenger.prototype.setDeparturePoint = function(departurePoint){
    this.departurePoint = departurePoint;
}
Passenger.prototype.setPlaceOfArrival = function(placeOfArrival){
    this.placeOfArrival = placeOfArrival;
}

Passenger.prototype.setPhoneNumber = function(phoneNumber){
    this.phoneNumber = phoneNumber;
}

Passenger.prototype.setFullName = function(fullName){
    this.fullName = fullName;
}

Passenger.prototype.setPaymentOption = function(paymentOption){
    this.paymentOption = paymentOption;
}

Passenger.prototype.setPrice = function(price){
    this.price = price;
}

Passenger.prototype.setDeparturePointLink = function(departurePointLink){
    this.departurePointLink = departurePointLink;
}
Passenger.prototype.setPlaceOfArrivalLink = function(placeOfArrivalLink){
    this.placeOfArrivalLink = placeOfArrivalLink;
}

Passenger.prototype.setPhoneNumberLink = function(phoneNumberLink){
    this.phoneNumberLink = phoneNumberLink;
}

Passenger.prototype.setFullNameLink = function(fullNameLink){
    this.fullNameLink = fullNameLink;
}

Passenger.prototype.setPaymentOptionLink = function(paymentOptionLink){
    this.paymentOptionLink = paymentOptionLink;
}

Passenger.prototype.setPriceLink = function(priceLink){
    this.priceLink = priceLink;
}


function SPRINTER_CALC_MAC() {


    var spreadsheet = SpreadsheetApp.getActive().getSheetByName("SPRINTERBLUE");
    var spreadsheetNameFor = spreadsheet.getRange("H5").getValue();
//  if(content=="") return;
    var spreadsheetFor = SpreadsheetApp.getActive().getSheetByName(spreadsheetNameFor);
    var kop = new Passenger("dich","dich","dich","dich","dich","dich");

    var kopVal = kop.getDeparturePointLink();

    var passengers = instantiatePassengers();

//  
//  var queue = [];
//  queue.push("Запорожье");
//  queue.push("Харьков");
//  var iop = queue.indexOf("Харьков");
//    var content = spreadsheet.getRange("H5").getValue();
//  
//  var dich = new Place("Cho za dichka");
//  
//  var dichType = dich instanceof Place;
//  if(content=="") return;




    for (var prop in sprinterKeysMap){

        var str = '=' + content + sprinterKeysMap[prop];
        spreadsheet.getRange(prop).setValue('=' + content + '!' +sprinterKeysMap[prop]);

    };


};