import { Log } from "./log";

export interface JobAudit {
    auditId : string
    joibId : string
    job : any
    startTime : Date
    endTime : Date
    logs : Log[]
}