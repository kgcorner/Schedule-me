import { Time } from "@angular/common";

export interface RecordProcessorMonthlyJob {
    jobId : string
    name : string
    urgent : boolean
    startTime : Time
    status : string
    jobParam : any
    jobKind : string
    dayOfMonth  :number
    lastCompleteRunStartedAt : Date
    lastCompletedRunEndedAt : Date
    recordCount : number
    recordProcessed : number
}