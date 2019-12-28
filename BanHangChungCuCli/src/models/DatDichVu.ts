import { DichVu } from "@/models/DichVu";
import { Users } from "@/models/Users";

export interface DatDichVu {
    datDichVuID: number;
    userID: number;
    dichVuID: number;
    ngayDat: Date;
    yeuCau: string;
    ghiChu: string;
    dichVu?: DichVu;
    users?: Users;
}
