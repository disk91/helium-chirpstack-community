declare module "vue/types/message" {
    interface Message {
        id : string,
        index : bigint,
        type : bigint,
        category : bigint,
        content : string,
        until : bigint,
        onlyone : boolean
    }
    interface UserMessage {
        type : bigint,
        category : bigint,
        content : string
    }
}
