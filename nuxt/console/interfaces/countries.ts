declare module "vue/types/countries" {

    interface Slim2Country {
        name : string;
        'alpha-2' : string;
        'country-code' : string;
    }

    interface Country {
        isoCode: string;
        name: string;
        flagLink: string;
    }

}