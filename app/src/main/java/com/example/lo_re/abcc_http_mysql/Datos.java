package com.example.lo_re.abcc_http_mysql;

public class Datos {
    private String nc;
    private String n;
    private String pa;
    private String sa;
    private String e;
    private String s;
    private String c;

    public Datos(String nc, String n, String pa, String sa, String e, String s, String c) {
        this.nc = nc;
        this.n = n;
        this.pa = pa;
        this.sa = sa;
        this.e = e;
        this.s = s;
        this.c = c;
    }

    public String getNc() {
        return nc;
    }

    public String getN() {
        return n;
    }

    public String getPa() {
        return pa;
    }

    public String getSa() {
        return sa;
    }

    public String getE() {
        return e;
    }

    public String getS() {
        return s;
    }

    public String getC() {
        return c;
    }
}
