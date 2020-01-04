<template>
    <v-flex xs12>
        <v-layout row wrap>
            <v-flex xs12><h3>Danh sách dịch vụ đã đặt</h3></v-flex>
            <v-flex xs12 md4>
                <v-text-field label="Tìm kiếm" v-model="searchParamsDatDichVu.tenDichVu" @input="getDataFromApi(searchParamsDatDichVu)"></v-text-field>
            </v-flex>
            <v-flex xs6 md2>
                <v-datepicker label="Từ ngày" v-model="searchParamsDatDichVu.tuNgay"
                              :max="searchParamsDatDichVu.tuNgay" @input="getDataFromApi(searchParamsDatDichVu)"></v-datepicker>
            </v-flex>
            <v-flex xs6 md2>
                <v-datepicker label="Đến ngày" v-model="searchParamsDatDichVu.denNgay"
                              :min="searchParamsDatDichVu.denNgay" @input="getDataFromApi(searchParamsDatDichVu)"></v-datepicker>
            </v-flex>
            <v-flex xs12>
                <v-layout row wrap>
                    <template v-for="item in dsDatDichVu">
                        <v-flex xs12 sm6>
                            <v-card style="margin: 5px;" :to="'/dich-vu/' + item.dichVuID">
                                <v-layout nowrap>
                                    <v-flex xs3>
                                        <v-img v-if="item.dichVu.anhDaiDien != null" slot="activator"
                                               :src="item.anhDaiDien"
                                               style="max-width: 100%;" id="img"
                                               aspect-ratio="1"
                                               class="grey lighten-2">
                                            <template v-slot:placeholder>
                                                <v-layout fill-height
                                                          align-center
                                                          justify-center
                                                          ma-0>
                                                    <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                                                </v-layout>
                                            </template>
                                        </v-img>
                                        <v-img v-else src='/static/images/photo-3x4.jpg' slot="activator"
                                               style="max-width: 100%;" id="img"
                                               aspect-ratio="1"
                                               class="grey lighten-2">
                                            <template v-slot:placeholder>
                                                <v-layout fill-height
                                                          align-center
                                                          justify-center
                                                          ma-0>
                                                    <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                                                </v-layout>
                                            </template>
                                        </v-img>
                                    </v-flex>
                                    <v-flex xs9>
                                        <v-card-title primary-title>
                                            <div style="width: 100%">
                                                <div class="headline">{{item.dichVu.tenDichVu}}</div>
                                                <div style="white-space: nowrap; text-overflow: ellipsis; white-space: nowrap; overflow: hidden; width: 100%;">Yêu cầu dịch vu: {{item.yeuCau}}</div>
                                                <div style="white-space: nowrap; text-overflow: ellipsis; white-space: nowrap; overflow: hidden; width: 100%;">Ghi chú: {{item.ghiChu}}</div>
                                            </div>
                                        </v-card-title>
                                    </v-flex>
                                </v-layout>
                            </v-card>
                        </v-flex>
                    </template>
                </v-layout>
            </v-flex>
            <v-dialog v-model="dialogConfirmDelete" max-width="290">
                <v-card>
                    <v-card-title class="headline">Xác nhận xóa</v-card-title>
                    <v-card-text class="pt-3">Bạn có chắc chắn muốn xóa?</v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn @click.native="dialogConfirmDelete=false" flat>Hủy</v-btn>
                        <v-btn color="red darken-1" @click.native="deleteDatDichVu" flat>Xác Nhận</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </v-layout>
        
    </v-flex>
</template>
<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import DatDichVuApi, { DatDichVuApiSearchParams } from '@/apiResources/DatDichVuApi';
    import { DatDichVu } from '@/models/DatDichVu';
    import { HTTP } from '@/http-servicesNew';
    import APIS from '@/apisServer';

    export default Vue.extend({
        components: {},
        data() {
            return {
                dialog: false,
                dsDatDichVu: [] as DatDichVu[],
                tableHeader: [
                    { text: '#', value: '#', align: 'center', sortable: false },
                    { text: 'Họ và tên', value: 'tenChuHo', align: 'center', sortable: true },
                    { text: 'Phòng', value: 'tenPhong', align: 'center', sortable: true },
                    { text: 'SĐT', value: 'soDienThoai', align: 'center', sortable: true },
                    { text: 'Dịch vụ', value: 'tenDichVu', align: 'center', sortable: true },
                    { text: 'Yêu cầu', value: 'yeuCau', align: 'center', sortable: true },
                    { text: 'Ghi chú', value: 'ghiChu', align: 'center', sortable: true },
                    { text: 'Thao tác', value: '#', align: 'center', sortable: false },
                ],
                searchParamsDatDichVu: { includeEntities: true, rowsPerPage: 0, userID: this.$store.state.user.User.UserId } as DatDichVuApiSearchParams,
                loadingTable: false,
                selectedDatDichVu: {} as DatDichVu,
                dialogConfirmDelete: false,
                dsToaNha: [] as any,
                datDichVu: {} as DatDichVu, 
                APIS: APIS
            }
        },
        watch: {
        },
        created() {
            //this.getDanhSachToaNha();
            this.getDataFromApi(this.searchParamsDatDichVu)
        },
        methods: {
            getDataFromApi(searchParamsDatDichVu: DatDichVuApiSearchParams): void {
                this.loadingTable = true;
                DatDichVuApi.search(searchParamsDatDichVu).then(res => {
                    this.dsDatDichVu = res as any;
                    //this.searchParamsDatDichVu.totalItems = res.pagination.totalItems;
                    this.loadingTable = false;
                });
            },
            showGhiNhan(datDichVu: DatDichVu) {
                this.dialog = true;
                this.datDichVu = datDichVu;
            },
            ghiNhan() {
                DatDichVuApi.update(this.datDichVu.datDichVuID, this.datDichVu).then(res => {
                    this.dialog = false;
                    this.getDataFromApi(this.searchParamsDatDichVu);
                    this.$snotify.success('Ghi nhận thành công!');
                }).catch(res => {
                    this.$snotify.error('Ghi nhận thất bại!');
                });
            },
            confirmDelete(datDichVu: DatDichVu): void {
                this.selectedDatDichVu = datDichVu;
                this.dialogConfirmDelete = true;
            },
            deleteDatDichVu(): void {
                DatDichVuApi.delete(this.selectedDatDichVu.datDichVuID).then(res => {
                    this.$snotify.success('Xóa thành công!');
                    this.getDataFromApi(this.searchParamsDatDichVu);
                    this.dialogConfirmDelete = false;
                }).catch(res => {
                    this.$snotify.error('Xóa thất bại!');
                });
            },
            // getDanhSachToaNha() {
            //     HTTP.get('odata/ToaNha').then(res => {
            //         this.dsToaNha.push({
            //             ToaNhaId: null as any,
            //             TenToaNha: 'Tất cả'
            //         } as any);
            //         this.dsToaNha.push(...res.data.value);
            //         this.searchParamsDatDichVu.toaNhaID = this.$store.state.user.User.ToaNhaId;
            //         this.getDataFromApi(this.searchParamsDatDichVu);
            //     })
            // },
        }
    });
</script>

