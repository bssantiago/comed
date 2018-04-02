import { Component, OnInit, forwardRef, Input, OnChanges } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR, FormControl, Validator, NG_VALIDATORS } from '@angular/forms';
import { filter } from 'lodash';

export function tagsValidator() {
  return (c: FormControl) => {
    console.log(c);
    const err = { tagError: { given: c.value } };
    return err;
  };
}

@Component({
  selector: 'app-tag-control',
  templateUrl: './tag-control.component.html',
  styleUrls: ['./tag-control.component.css'],
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => TagControlComponent),
    multi: true
  }, {
    provide: NG_VALIDATORS,
    useExisting: forwardRef(() => TagControlComponent),
    multi: true,
  }]
})
export class TagControlComponent implements Validator, ControlValueAccessor, OnChanges {

  @Input() private tags: Array<string> = [];
  private tag: string;

  propagateChange: any = () => { };
  validateFn: any = () => { };

  removeTag(tag: string) {
    this.tags = filter(this.tags, (x: any) => x !== tag);
    this.propagateChange(this.tags);
  }

  addTag() {
    this.tags.push(this.tagValue);
    this.propagateChange(this.tags);
  }


  get tagsValues() {
    return this.tags;
  }

  set tagsValues(array: Array<string>) {
    this.tags = array;
  }


  get tagValue() {
    return this.tag;
  }

  set tagValue(val) {
    this.tag = val;
  }

  ngOnChanges(inputs) {
    this.validateFn = tagsValidator();
  }


  writeValue(value: any): void {
    if (value) {
      this.tags = value;
    }
  }

  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any): void {
  }

  validate(c: FormControl) {
    console.log(c);
    return this.validateFn(c);
  }
}
