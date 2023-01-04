declare module "vue/types/userProfile" {
    interface UserDetails {
        username : string,
        company : string,
        firstName : string,
        lastName : string,
        address : string,
        cityCode : string,
        cityName : string
        country: string,
    }
    interface UserLogin {
        username : string,
        completion: string
    }
}
