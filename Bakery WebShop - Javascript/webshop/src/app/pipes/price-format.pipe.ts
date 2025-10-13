import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'priceFormat',
  standalone: false
})
export class PriceFormatPipe implements PipeTransform {

  transform(value: number, currencySymbol: string = '€'): string {
    if (value == null) return '';
    return `${currencySymbol} ${value.toFixed(2)}`;
  }
}
