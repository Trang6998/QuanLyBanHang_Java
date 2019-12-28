import { DonDatHang } from "@/models/DonDatHang";
import { SanPham } from "@/models/SanPham";

export interface ChiTietDonDatHang {
    chiTietDonDatHangID: number;
    donDatHangID: number;
    sanPhamID: number;
    soLuong: number;
    giaXuat: number;
    donDatHang?: DonDatHang;
    sanPham?: SanPham;
}
