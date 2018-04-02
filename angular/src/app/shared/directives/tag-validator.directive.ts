import { Directive, forwardRef } from '@angular/core';
import { NG_VALIDATORS, AbstractControl, ValidationErrors, Validator, FormControl } from '@angular/forms';

@Directive({
  selector: '[appTagValidator]'
})
export class TagValidatorDirective implements Validator {

  validate(c: FormControl): ValidationErrors | null {
    return this.validateTags;
  }

  private validateTags(control: FormControl): ValidationErrors | null {
    if (control.value.length === 0) {
      return { tagError: 'You need to select at least one tag.' };
    }
    return null;
  }

}
