declare module "vue/types/userSearch" {
    interface TenantEntry {
        name : string,
        admin : boolean,
        id : string,
    }

    interface UserListRespItf {
        userLogin : string,
        disable : boolean,
        registration : BigInt,
        lastLogin : BigInt,
        tenants : TenantEntry[],
    }

    interface UserBanReqItf {
        username : string,
    }
}