package com.judai.database_management;

public class SanPham {
    private String _nameSP;
    private int _amoutSP;
    private int _priceSP;

    public SanPham(String _nameSP, int _amoutSP, int _priceSP) {
        this._nameSP = _nameSP;
        this._amoutSP = _amoutSP;
        this._priceSP = _priceSP;
    }

    public String get_nameSP() {
        return _nameSP;
    }

    public void set_nameSP(String _nameSP) {
        this._nameSP = _nameSP;
    }

    public int get_amoutSP() {
        return _amoutSP;
    }

    public void set_amoutSP(int _amoutSP) {
        this._amoutSP = _amoutSP;
    }

    public int get_priceSP() {
        return _priceSP;
    }

    public void set_priceSP(int _priceSP) {
        this._priceSP = _priceSP;
    }
}
