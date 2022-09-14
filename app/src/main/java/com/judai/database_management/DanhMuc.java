package com.judai.database_management;

public class DanhMuc {
    private String _name_DM;

    public DanhMuc(String _nameDM) {
        this._name_DM = _nameDM;
    }

    public String get_nameDM() {
        return _name_DM;
    }

    public void set_nameDM(String _nameDM) {
        this._name_DM = _nameDM;
    }
}
