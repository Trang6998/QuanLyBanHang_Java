import { HTTP } from '@/HTTPServices'
import { BaseApi } from './BaseApi'
import { PaginatedResponse,Pagination } from './PaginatedResponse'
import { DonViTinh } from '@/models/DonViTinh'
export interface DonViTinhApiSearchParams extends Pagination {
}
class DonViTinhApi extends BaseApi {
    search(searchParams: DonViTinhApiSearchParams): Promise<PaginatedResponse<DonViTinh>> {

        return new Promise<PaginatedResponse<DonViTinh>>((resolve: any, reject: any) => {
            HTTP.get('donvitinh', {
                params: searchParams
            }).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    detail(id: number): Promise<DonViTinh> {
        return new Promise<DonViTinh>((resolve: any, reject: any) => {
            HTTP.get('donvitinh/' + id).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    update(id: number, donViTinh: DonViTinh): Promise<DonViTinh> {
        return new Promise<DonViTinh>((resolve: any, reject: any) => {
            HTTP.put('donvitinh/' + id, 
                donViTinh
            ).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    insert(donViTinh: DonViTinh): Promise<DonViTinh> {
        return new Promise<DonViTinh>((resolve: any, reject: any) => {
            HTTP.post('donvitinh', 
                donViTinh
            ).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    delete(id: number): Promise<DonViTinh> {
        return new Promise<DonViTinh>((resolve: any, reject: any) => {
            HTTP.delete('donvitinh/' + id)
            .then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
}
export default new DonViTinhApi();
