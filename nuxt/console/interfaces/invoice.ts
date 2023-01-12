declare module "vue/types/invoice" {
    interface InvoiceSetupGetRespItf {
        companyName : string,
        companyAddress : string,
        companyVAT : string,
        companyRegistration : string,
    }
    interface InvoiceSetupPutReqItf {
        companyName : string,
        companyAddress : string,
        companyVAT : string,
        companyRegistration : string,
    }
}