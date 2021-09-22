package bitway.model;

import java.io.File;
import java.util.ArrayList;

public class CEP {

    private int id;

    private String cidade;
    private String logradouro;
    private String CEP; //Easier to work, just parses to the data you need.
    private String complemento;
    private String bairro;
    private String UF;


    //private Date DataPesquisa; //Stores when user searched
    private String GIA;
    private int DDD;
    private int SIAFI;
    private int IBGE;

    @SuppressWarnings("unused")
    public CEP() {
    }

    public CEP(int id, String cidade, String logradouro, String CEP, String complemento, String bairro, String UF, String GIA, int DDD, int SIAFI, int IBGE) {
        this.id = id;
        this.cidade = cidade;
        this.logradouro = logradouro;
        this.CEP = CEP;
        this.complemento = complemento;
        this.bairro = bairro;
        this.UF = UF;
        this.GIA = GIA;
        this.DDD = DDD;
        this.SIAFI = SIAFI;
        this.IBGE = IBGE;
    }

    public void CreateCSV(CEP c){
        File file;
        ArrayList<String> test = new ArrayList<>();
        file = EasyCSV.CreateCSV("db.csv");

        test.add(c.getCEP() + ", " + c.getCidade() + ", " + c.IBGE);

        EasyCSV.WriteCSV(test, file);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getUF() {
        return UF;
    }

    public void setUF(String UF) {
        this.UF = UF;
    }

    public String getGIA() {
        return GIA;
    }

    public void setGIA(String GIA) {
        this.GIA = GIA;
    }

    public int getDDD() {
        return DDD;
    }

    public void setDDD(int DDD) {
        this.DDD = DDD;
    }

    public int getSIAFI() {
        return SIAFI;
    }

    public void setSIAFI(int SIAFI) {
        this.SIAFI = SIAFI;
    }

    public int getIBGE() {
        return IBGE;
    }

    public void setIBGE(int IBGE) {
        this.IBGE = IBGE;
    }
}