// This must return the name of the ADR algorithm.
export function name() {
    return "ADR disabled";
}

// This must return the id of the ADR algorithm.
export function id() {
    return "adr-disabled";
}

export function handle(req) {
    let resp = {
        dr: req.dr,
        txPowerIndex: req.txPowerIndex,
        nbTrans: req.nbTrans
    }
    return resp;
}
