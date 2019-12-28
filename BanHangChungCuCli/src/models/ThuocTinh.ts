import { ThuocTinhSanPham } from "@/models/ThuocTinhSanPham";

export interface ThuocTinh {
    thuocTinhID: number;
    tenThuocTinh: string;
    ghiChu: string;
    thuocTinhSanPham?: ThuocTinhSanPham[];
}
