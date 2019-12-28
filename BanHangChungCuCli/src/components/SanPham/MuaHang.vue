<template>
    <v-dialog v-model="dialog" max-width="700">
        <v-container pa-0 grid-list-md>
            <v-card>
                <v-card-title class="primary white--text" style="height: 40px">
                    <h3>Mua hàng</h3>
                    <v-spacer></v-spacer>
                    <v-btn class="white--text ma-0" small @click="hideDonMuaNgay" icon dark><v-icon>close</v-icon></v-btn>
                </v-card-title>
                <v-card-text>
                    <div>Bạn có thể nhận hàng trong khoảng thời gian nào:</div>
                    <v-layout row wrap>
                        <v-flex xs12>
                            <v-radio-group v-model="thoigian" row>
                                <v-radio label="Mọi thời gian" value="moithoigian"></v-radio>
                                <v-radio label="Theo khung giờ " value="theokhunggio"></v-radio>
                            </v-radio-group>
                        </v-flex>
                        <v-flex xs12 md6>
                            <v-datetimepicker v-model="donMuaNgay.henLayTu" label="Từ ngày"
                                              clearable></v-datetimepicker>
                        </v-flex>
                        <v-flex xs12 md6>
                            <v-datetimepicker v-model="donMuaNgay.henLayDen" label="Đến ngày"
                                              clearable></v-datetimepicker>
                        </v-flex>
                        <v-flex xs12>
                            <v-textarea rows="3" v-model="donMuaNgay.ghiChu"
                                        label="Ghi chú"
                                        :error-messages="errors.collect('Ghi chú', 'frmAddEdit')"
                                        v-validate="''"
                                        data-vv-scope="frmAddEdit"
                                        data-vv-name="Ghi chú"
                                        hide-details>
                            </v-textarea>
                        </v-flex>
                    </v-layout>
                </v-card-text>
                <v-card-actions>
                    <v-layout nowrap>
                        <v-spacer></v-spacer>
                        <v-btn class="primary" flat small>Mua ngay</v-btn>
                    </v-layout>
                </v-card-actions>
            </v-card>
        </v-container>
    </v-dialog>
</template>

<script lang="ts">
</script>

<style scoped>
</style>