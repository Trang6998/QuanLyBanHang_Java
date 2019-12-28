import { HTTP } from '@/HTTPServices'
import { BaseApi } from './BaseApi'
import { PaginatedResponse,Pagination } from './PaginatedResponse'
import { ThuocTinh } from '@/models/ThuocTinh'
export interface ThuocTinhApiSearchParams extends Pagination {
    tenThuocTinh?: string;
}
class ThuocTinhApi extends BaseApi {
    search(searchParams: ThuocTinhApiSearchParams): Promise<PaginatedResponse<ThuocTinh>> {

        return new Promise<PaginatedResponse<ThuocTinh>>((resolve: any, reject: any) => {
            HTTP.get('thuoctinh', {
                params: searchParams
            }).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    detail(id: number): Promise<ThuocTinh> {
        return new Promise<ThuocTinh>((resolve: any, reject: any) => {
            HTTP.get('thuoctinh/' + id).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    update(id: number, thuocTinh: ThuocTinh): Promise<ThuocTinh> {
        return new Promise<ThuocTinh>((resolve: any, reject: any) => {
            HTTP.put('thuoctinh/' + id, 
                thuocTinh
            ).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    insert(thuocTinh: ThuocTinh): Promise<ThuocTinh> {
        return new Promise<ThuocTinh>((resolve: any, reject: any) => {
            HTTP.post('thuoctinh', 
                thuocTinh
            ).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    delete(id: number): Promise<ThuocTinh> {
        return new Promise<ThuocTinh>((resolve: any, reject: any) => {
            HTTP.delete('thuoctinh/' + id)
            .then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
}
export default new ThuocTinhApi();
