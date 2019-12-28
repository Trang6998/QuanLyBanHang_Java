import { HTTP } from '@/HTTPServices'
import { BaseApi } from './BaseApi'
import { PaginatedResponse,Pagination } from './PaginatedResponse'
import { FileUploads } from '@/models/FileUploads'
export interface FileUploadsApiSearchParams extends Pagination {
}
class FileUploadsApi extends BaseApi {
    search(searchParams: FileUploadsApiSearchParams): Promise<PaginatedResponse<FileUploads>> {

        return new Promise<PaginatedResponse<FileUploads>>((resolve: any, reject: any) => {
            HTTP.get('fileuploads', {
                params: searchParams
            }).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    detail(id: number): Promise<FileUploads> {
        return new Promise<FileUploads>((resolve: any, reject: any) => {
            HTTP.get('fileuploads/' + id).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    update(id: number, fileUploads: FileUploads): Promise<FileUploads> {
        return new Promise<FileUploads>((resolve: any, reject: any) => {
            HTTP.put('fileuploads/' + id, 
                fileUploads
            ).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    insert(fileUploads: FileUploads): Promise<FileUploads> {
        return new Promise<FileUploads>((resolve: any, reject: any) => {
            HTTP.post('fileuploads', 
                fileUploads
            ).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    delete(id: number): Promise<FileUploads> {
        return new Promise<FileUploads>((resolve: any, reject: any) => {
            HTTP.delete('fileuploads/' + id)
            .then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
}
export default new FileUploadsApi();
