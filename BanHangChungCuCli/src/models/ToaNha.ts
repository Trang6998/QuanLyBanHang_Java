
import { Users } from "@/models/Users";

export interface ToaNha {
    ToaNhaId: number;
    TenToaNha: string;
    DiaChi: string;
    Users?: Users[];
}
