import { Time } from "@angular/common";

export interface RecordProcessorDailyJob {
    jobId : string
    name : string
    urgent : boolean
    startTime : Time
    status : string
    jobParam : any
    jobKind : string
    lastCompleteRunStartedAt : Date
    lastCompletedRunEndedAt : Date
    recordCount : number
    recordProcessed : number
}