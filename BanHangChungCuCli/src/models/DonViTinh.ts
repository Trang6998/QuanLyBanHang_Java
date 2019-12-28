import { SanPham } from "@/models/SanPham";

export interface DonViTinh {
    donViTinhID: number;
    tenDonVi: string;
    ghiChu: string;
    sanPham?: SanPham[];
}
