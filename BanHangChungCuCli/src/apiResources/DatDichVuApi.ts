import { HTTP } from '@/HTTPServices'
import { BaseApi } from './BaseApi'
import { PaginatedResponse,Pagination } from './PaginatedResponse'
import { DatDichVu } from '@/models/DatDichVu'
export interface DatDichVuApiSearchParams extends Pagination {
    userID?:number;
    dichVuID?: number;
    tuNgay?: Date;
    denNgay?: Date;
    toaNhaID?: number;
    phongID?: number;
    tenDichVu?: string;
}
class DatDichVuApi extends BaseApi {
    search(searchParams: DatDichVuApiSearchParams): Promise<PaginatedResponse<DatDichVu>> {

        return new Promise<PaginatedResponse<DatDichVu>>((resolve: any, reject: any) => {
            HTTP.get('datdichvu', {
                params: searchParams
            }).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    detail(id: number): Promise<DatDichVu> {
        return new Promise<DatDichVu>((resolve: any, reject: any) => {
            HTTP.get('datdichvu/' + id).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    update(id: number, datDichVu: DatDichVu): Promise<DatDichVu> {
        return new Promise<DatDichVu>((resolve: any, reject: any) => {
            HTTP.put('datdichvu/' + id, 
                datDichVu
            ).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    insert(datDichVu: DatDichVu): Promise<DatDichVu> {
        return new Promise<DatDichVu>((resolve: any, reject: any) => {
            HTTP.post('datdichvu', 
                datDichVu
            ).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    delete(id: number): Promise<DatDichVu> {
        return new Promise<DatDichVu>((resolve: any, reject: any) => {
            HTTP.delete('datdichvu/' + id)
            .then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
}
export default new DatDichVuApi();
