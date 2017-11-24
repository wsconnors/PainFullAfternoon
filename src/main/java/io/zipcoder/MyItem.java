package io.zipcoder;

public class MyItem {

    private String name,type, expiration;
    private double price;
    MyItem(String name,String price,String type,String expiration){
        this.name = convertAllToLetters(name);
        this.type = convertAllToLetters(type);
        this.expiration = expiration;

        this.price = Double.parseDouble(price);
    }

    String convertAllToLetters(String str){
        StringBuilder builder = new StringBuilder();
        for(int i = 0;i<str.length();i++){
            char currentChar = str.charAt(i);
            if(!Character.isLetter(currentChar)){
                currentChar = convertToLetter(currentChar);
            }
            builder.append(currentChar);
        }
        return builder.toString();
    }

    char convertToLetter(char c){
        if(c == '0'){
            return 'o';
        }else if(c == '1'){
            return 'l';
        }
        return '?';
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString(){
        return name+" "+price+" "+type+" "+ expiration;
    }
}


