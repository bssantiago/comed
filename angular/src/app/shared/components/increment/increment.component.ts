import { Component, OnInit, forwardRef, Input, OnChanges } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR, FormControl, Validator, NG_VALIDATORS, ValidationErrors } from '@angular/forms';

export function createCounterRangeValidator(maxValue, minValue) {
  return (c: FormControl) => {
    const err = {
      rangeError: {
        given: c.value,
        max: maxValue || 10,
        min: minValue || 0
      }
    };
    console.log(c);
    return (c.value > +maxValue || c.value < +minValue) ? err : null;
  };
}

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => IncrementComponent),
  multi: true
};

export const VALIDATOR: any = {
  provide: NG_VALIDATORS,
  useExisting: forwardRef(() => IncrementComponent),
  multi: true,
};


@Component({
  selector: 'app-counter',
  templateUrl: './increment.component.html',
  styleUrls: ['./increment.component.css'],
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR, VALIDATOR]
})

export class IncrementComponent implements ControlValueAccessor, Validator, OnChanges {

  @Input() counterRangeMax;
  @Input() counterRangeMin;
  @Input() _counterValue = 0;


  propagateChange: any = () => { };
  validateFn: any = () => { };

  get counterValue() {
    return this._counterValue;
  }

  set counterValue(val) {
    this._counterValue = val;
    this.propagateChange(val);
  }

  increase() {
    this.counterValue++;
  }

  decrease() {
    this.counterValue--;
  }

  ngOnChanges(inputs) {
    if (inputs.counterRangeMax || inputs.counterRangeMin) {
      this.validateFn = createCounterRangeValidator(this.counterRangeMax, this.counterRangeMin);
      this.propagateChange(this.counterValue);
    }
  }

  writeValue(value) {
    if (value) {
      this.counterValue = value;
    }
  }

  registerOnChange(fn) {
    this.propagateChange = fn;
  }

  registerOnTouched() { }



  validate(c: FormControl): ValidationErrors {
    console.log(c);
    const err = {
      incrementError: {
        given: c.value
      }
    };
    return { creditCard: 'A credit card number must be 16-digit long' };
    // return this.validateFn(c);
  }


}
