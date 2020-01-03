import { ChiTietDonDatHang } from "@/models/ChiTietDonDatHang";
import { Users } from "@/models/Users";

export interface DonDatHang {
    donDatHangID: number;
    taiKhoanDatHangID: number;
    taiKhoanNhanVienID: number;
    soHieuDon: string;
    ngayDat: Date;
    henLayTu: Date;
    henLayDen: Date;
    henTraTu: Date;
    henTraDen: Date;
    laTre: boolean;
    lyDoTraTre: string;
    ngayGioTraThucTe: Date;
    ghiChu: string;
    tinhTrang: number;
    users?: Users; // taikhoandathang
    users1?: Users; // taikhoannhanvien
    lstChiTietDonDatHang?: ChiTietDonDatHang[];
}
