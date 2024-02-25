declare module "vue/types/standards" {

  interface SelectOption {
        value: number;
        text: string;
  }

  interface TableField {
    key: string;
    label: any;
    sortable?: boolean;
    stickyColumn?: boolean;
    isRowHeader?: boolean;
  }

}