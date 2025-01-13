declare module "vue/types/userProfile" {
    interface UserDetails {
        username : string,
        company : string,
        company_tax : string,
        firstName : string,
        lastName : string,
        address : string,
        cityCode : string,
        cityName : string
        country: string,
        country_iso: string,
    }
    interface UserLogin {
        username : string,
        completion: string
    }
}
