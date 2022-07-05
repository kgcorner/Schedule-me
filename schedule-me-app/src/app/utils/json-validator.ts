import { AbstractControl } from "@angular/forms";

export function jsonValidator(control: AbstractControl) {
    try {
        let value = control.value;
        JSON.parse(value);
        return null;
    } catch(x) {
        return { invalidParam: true };
    }
    
}