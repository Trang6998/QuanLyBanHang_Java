import { DatDichVu } from "@/models/DatDichVu";
import { DonDatHang } from "@/models/DonDatHang";
import { SanPhamNhaCungCap } from "@/models/SanPhamNhaCungCap";

export interface Users {
    passwordSalt: string;
    active: boolean;
    userId: number;
    userName: string;
    email: string;
    hoGiaDinhId: number;
    toaNhaId: number;
    loaiTaiKhoanId: number;
    phongId: number;
    password: string;
    createdTime: Date;
    lastLogin: Date;
    lang: string;
    datDichVu?: DatDichVu[];
    donDatHang?: DonDatHang[];
    donDatHang1?: DonDatHang[];
    sanPhamNhaCungCap?: SanPhamNhaCungCap[];
}
