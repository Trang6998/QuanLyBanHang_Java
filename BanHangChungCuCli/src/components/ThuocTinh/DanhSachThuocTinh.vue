<template>
    <v-flex xs12>
        <v-breadcrumbs divider="/" class="pa-0">
            <v-icon slot="divider">chevron_right</v-icon>
            <v-breadcrumbs-item>
                <v-btn flat class="ma-0" @click="$router.go(-1)" small><v-icon>arrow_back</v-icon> Quay lại</v-btn>
            </v-breadcrumbs-item>
            <v-breadcrumbs-item to="/thuoctinh" exact>ThuocTinh</v-breadcrumbs-item>
        </v-breadcrumbs>
        <v-card>
            <v-card-text>
                <v-layout row wrap>
                    <v-flex xs12>
                    <v-data-table :headers="tableHeader"
                                :items="dsThuocTinh"
                                @update:pagination="getDataFromApi" :pagination.sync="searchParamsThuocTinh"
                                :total-items="searchParamsThuocTinh.totalItems"
                                :loading="loadingTable"
                                class="table-border table">
                        <template slot="items" slot-scope="props">
                    <td>{{ props.item.ThuocTinhID }}</td>
                    <td>{{ props.item.TenThuocTinh }}</td>
                    <td>{{ props.item.GhiChu }}</td>
                    <td class="text-xs-center" style="width:80px;">
                        <v-btn flat icon small :to="'/thuoctinh/'+props.item.ThuocTinhID" class="ma-0">
                            <v-icon small>edit</v-icon>
                        </v-btn>
                        <v-btn flat color="red" icon small class="ma-0" @click="confirmDelete(props.item)">
                            <v-icon small>delete</v-icon>
                        </v-btn>
                    </td>
                            </template>
                        </v-data-table>
                    </v-flex>
                </v-layout>
            </v-card-text>
        </v-card>
        <v-dialog v-model="dialogConfirmDelete" max-width="290">
                    <v-card>
                <v-card-title class="headline">Xác nhận xóa</v-card-title>
                <v-card-text class="pt-3">Bạn có chắc chắn muốn xóa?</v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn @click.native="dialogConfirmDelete=false" flat>Hủy</v-btn>
                    <v-btn color="red darken-1" @click.native="deleteThuocTinh" flat>Xác Nhận</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-flex>
</template>
<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import ThuocTinhApi, { ThuocTinhApiSearchParams } from '@/apiResources/ThuocTinhApi';
    import { ThuocTinh } from '@/models/ThuocTinh';

    export default Vue.extend({
        components: {},
        data() {
            return {
                dsThuocTinh: [] as ThuocTinh[],
                tableHeader: [
                    { text: 'ThuocTinhID', value: 'ThuocTinhID', align: 'center', sortable: true },
                    { text: 'TenThuocTinh', value: 'TenThuocTinh', align: 'center', sortable: true },
                    { text: 'GhiChu', value: 'GhiChu', align: 'center', sortable: true },
                    { text: 'Thao tác', value: '#', align: 'center', sortable: false },
                ],
                searchParamsThuocTinh: { includeEntities: true, rowsPerPage: 10 } as ThuocTinhApiSearchParams,
                loadingTable: false,
                selectedThuocTinh: {} as ThuocTinh,
                dialogConfirmDelete: false,
            }
        },
        watch: {
        },
        created() {
            this.getDataFromApi(this.searchParamsThuocTinh);
        },
        methods: {
            getDataFromApi(searchParamsThuocTinh: ThuocTinhApiSearchParams): void {
                this.loadingTable = true;
                ThuocTinhApi.search(searchParamsThuocTinh).then(res => {
                    this.dsThuocTinh = res.data;
                    this.searchParamsThuocTinh.totalItems = res.pagination.totalItems;
                    this.loadingTable = false;
                });
            },
            confirmDelete(thuocTinh: ThuocTinh): void {
                this.selectedThuocTinh = thuocTinh;
                this.dialogConfirmDelete = true;
            },
            deleteThuocTinh(): void {
                ThuocTinhApi.delete(this.selectedThuocTinh.thuocTinhID).then(res => {
                    this.$snotify.success('Xóa thành công!');
                    this.getDataFromApi(this.searchParamsThuocTinh);
                    this.dialogConfirmDelete = false;
                }).catch(res => {
                    this.$snotify.error('Xóa thất bại!');
                });
            },
        }
    });
</script>

