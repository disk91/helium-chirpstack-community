// This must return the name of the ADR algorithm.
export function name() {
    return "Force DR1";
}

// This must return the id of the ADR algorithm.
export function id() {
    return "force-dr1";
}

export function handle(req) {
    let resp = {
        dr: 1,
        txPowerIndex: req.maxTxPowerIndex,
        nbTrans: req.nbTrans
    }
    return resp;
}