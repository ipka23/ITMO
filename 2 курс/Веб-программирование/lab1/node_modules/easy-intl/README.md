# Easy Intl 🌎

### Utility for easy use of the <a href="https://tc39.es/ecma402/">`Internationalization API`</a>

- [Specification](https://tc39.es/ecma402/)
- [Language Code Identifiers](https://github.com/libyal/libfwnt/wiki/Language-Code-identifiers)
- [Currency Codes](https://en.wikipedia.org/wiki/ISO_4217#Active_codes)
- [Database Time Zones](https://en.wikipedia.org/wiki/List_of_tz_database_time_zones#List)
- [Example Units](https://unicode.org/reports/tr35/tr35-general.html#Example_Units)

### Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [HTML attributes](#html-attributes)
- [Signature of EasyIntl class](#signature-of-easyintl-class)
- [Utils signatures](#utils-signatures)
  - [`formatDateTime`](#formatdatetime)
  - [`formatRelativeTime`](#formatrelativetime)
  - [`formatNumber`](#formatnumber)
  - [`formatNames`](#formatnames)
  - [`formatList`](#formatlist)
  - [`compareValues`](#comparevalues)
  - [`pluralize`](#pluralize)

## Installation

```bash
yarn add easy-intl
# or
npm i easy-intl
```

## Usage

[Full example on Codepen](https://codepen.io/harryheman/pen/QWgKGry)

```html
<main data-intl_root id="global_root">
  <h1
    data-intl_type="custom"
    data-intl_value="main_title"
    data-intl_map="main_title"
  >
    easy Intl
  </h1>
  <section class="card">
    <img
      src="https://cdn-icons-png.flaticon.com/512/814/814513.png"
      alt=""
      role="presentation"
    />
    <select id="locale">
      <option value="ru-RU">Ru</option>
      <option value="en-US" selected>En</option>
      <option value="de-DE">De</option>
      <option value="ja-JP">Ja</option>
    </select>
  </section>

  <div class="container">
    <section class="card">
      <h2
        data-intl_type="custom"
        data-intl_value="date_title"
        data-intl_map="date_title"
      >
        Дата и время
      </h2>
      <div class="box">
        <h3 data-intl_type="custom" data-intl_value="without_options">
          Без встроенных настроек
        </h3>
        <time data-intl_type="date" data-intl_value="2021-08-16 12:00">
          16.08.2021 12:00
        </time>
      </div>
      <div class="box">
        <h3 data-intl_type="custom" data-intl_value="with_options">
          Со встроенными настройками
        </h3>
        <time
          data-intl_type="date"
          data-intl_value="2021-08-16 12:00"
          data-intl_options="dateStyle: long, timeStyle: 'short'"
        >
          16 августа 2021 г., 12:00
        </time>
      </div>
    </section>

    <section data-intl_root id="local_root" class="card">
      <h2 data-intl_type="custom" data-intl_value="date_title">
        Дата и время
      </h2>
      <div class="box">
        <h3 data-intl_type="custom" data-intl_value="without_options">
          Без встроенных настроек
        </h3>
        <time data-intl_type="date" data-intl_value="2021-08-16 12:00">
          16.08.2021 12:00
        </time>
      </div>
      <div class="box">
        <h3 data-intl_type="custom" data-intl_value="with_options">
          Со встроенными настройками
        </h3>
        <time
          data-intl_type="date"
          data-intl_value="2021-08-16 12:00"
          data-intl_options="day: 'numeric', month: 'numeric', year: 'numeric', hour: '2-digit', minute: '2-digit'"
        >
          16 августа 2021 г., 12:00
        </time>
      </div>
    </section>
  </div>
</main>
```

```js
import { EasyIntl } from 'easy-intl'
import options from './intl/options.js'
import dictionary from './intl/dictionary.js'

// global localization
const globalIntl = new EasyIntl({
  locale: 'en-US',
  root: '#global_root',
  dictionary,
  ...options
})

console.log(globalIntl.elements)
console.log(globalIntl.map)
console.log(globalIntl.map['main_title'])

// local localization
const localIntl = new EasyIntl({
  autorun: false,
  dictionary,
  date: { day: '2-digit', month: 'long', year: 'numeric' }
})
localIntl.locale = 'ja-JP'
localIntl.root = '#local_root'
// manual localization
localIntl.localize()

const localeSelector = document.querySelector('#locale')
localeSelector.onchange = (e) => {
  // auto localization
  globalIntl.locale = e.target.value
}
```

```js
// options
export default {
  date: { dateStyle: 'short' },
  relative: { numeric: 'auto' },
  number: { style: 'decimal' },
  names: { type: 'language' },
  list: { type: 'conjunction' }
}
```

```js
// dictionary
export default {
  'ru-RU': {
    main_title: 'Пример использования Easy Intl',
    without_options: 'Без встроенных настроек',
    with_options: 'Со встроенными настройками',
    locale_title: 'Локаль',
    date_title: 'Дата и время',
    relative_title: 'Относительное время',
    number_title: 'Число',
    currency_title: 'Валюта',
    unit_title: 'Единицы измерения',
    names_title: 'Названия языков и т.д.',
    list_title: 'Список'
  },
  'en-US': {
    main_title: 'Example of use Easy Intl',
    without_options: 'Without inline options',
    with_options: 'With inline options',
    locale_title: 'Locale',
    date_title: 'Date and time',
    relative_title: 'Relative time',
    number_title: 'Number',
    currency_title: 'Currency',
    unit_title: 'Units',
    names_title: 'Language names etc.',
    list_title: 'List'
  }
}
```

By default `EasyIntl` localize `textContent` property, `title` and `placeholder` attribute values of HTML elements with `data-intl_type` attribute. Use `intl.map` object if you need to localize other things (don't forget to add `data-intl_map` attribute to corresponding elements).

## HTML attributes

- `data-intl_root` - indicator for root HTML element (when global and local (or multiple local) localizations used)
- `data-intl_type` - `date` | `relative` | `number` | `names` | `list` | `compare` | `plural` | `custom`
- `data-intl_value`
  - for `date` - any valid `date` | `time` | `datetime`
  - for `relative` - `string` with `amount` and `unit`, e.g. `1 day` | `-1_week` (separators: space | underscore)
  - for `number` - `number` | `string`
  - for `names` - language | country code
  - for `list` - `array` | `string`, e.g `['Chrome', 'Firefox', 'Safari']` | `Chrome, Firefox, Safari`. `string` may include square brackets `[]`, single, double or back quotes `'"`, e.g. `Chrome, 'Firefox'; "Safari"` (separators: comma | semicolon)
  - for `compare` - `array` | `string` (⬆)
  - for `plural` - `number`
  - for `custom` - `string`: key from dictionary
- `data-intl_options` - any valid options for corresponding util (depends on `data-intl_type`; ⬇)
- `data-intl_map` - `string`: key for `intl.map: { [data-intl_map]: localized data-intl_value }`

## Signature of EasyIntl class

```js
const intl = new EasyIntl(options)
```

__Options__

- `locale: string` - language | country code
- `root: HTMLElement | string` - `HTMLElement` | any valid `CSS-selector`
- `autorun: boolean` - if `true`, localization will be execute on every `locale` property update
- `dictionary: { [key: string]: string }` - dictionary for custom localization
- `date: { [key: string]: string }` - options for `date` method (`formatDateTime` util)
- `relative: { [key: string]: string }` - options for `relative` method (`formatRelativeTime` util)
- `number: { [key: string]: string }` - options for `number` method (`formatNumber` util)
- `names: { [key: string]: string }` - options for `names` method (`formatNames` util)
- `list: { [key: string]: string }` - options for `list` method (`formatList` util)
- `compare: { [key: string]: string }` - options for `compare` method (`compareValues` util)
- `plural: { [key: string]: string }` - options for `plural` method (`pluralize` util)

_Default options_

```js
{
  locale: [],
  root: document.body,
  autorun: true,
  options: {},
  dictionary: {},
  elements: [],
  map: {}
}
```

__Props (getters/setters)__

- `intl.locale: string`
- `intl.root: HTMLElement | string`
- `intl.autorun: boolean`
- `intl.dictionary: { [key: string]: string }`
- `intl.elements: HTMLElement[]` - HTML elements with `data-intl_type` attribute - `readonly`
- `intl.map: { [key: string]: string }` - `key` is the value of `data-intl_map` attribute, `value` is the localized value of `data-intl_value` attribute - `readonly`
- `intl.options: { [key: string]: { [key: string]: string } }` = options for corresponding methods

Setting `locale` property results in auto localization when `autorun: true`.

__Methods__

```js
intl.date(date: Date, options)

intl.relative(value: string, options)

intl.number(number: number, options)

intl.names(localeOf: string, options)

intl.list(list: array | string, options)

intl.compare(values: array | string, options)

intl.plural(number: number, options)

intl.custom(value: string) // dictionary must be provided

intl.localize(options) // locale, root, date, relative etc.
```

## Utils signatures

_Common options for all utils_

- `localeMatcher` - `lookup` | `best fit`
- `formatMatcher` - `basic` | `best fit`

### `formatDateTime`

__Util for [`Intl.DateTimeFormat`](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Intl/DateTimeFormat)__

_Signature_

```js
formatDateTime(options) // intl.date(date, options)
```

_Options_

- `locale` - language | country code
- `date` - any valid `date` | `time` | `datetime`
- `timeZone` - `UTC`, `America/New_York`, `Europe/Paris` etc.
- `calendar` - `chinese`, `gregory`, `hebrew`, `indian`, `islamic` etc.
- `numberingSystem` - `arab`, `beng`, `fullwide`, `latin` etc.
- `hour12: boolean` - if `true`, time will be present as `12pm`, `4am` etc.
- `hourCycle` - `h11`, `h12`, `h23`, `h24` etc.
- `dateStyle` - `full` | `long` | `medium` | `short`
- `weekday` - `long` | `short` | `narrow`
- `day` - `numeric` | `2-digit`
- `month` - `numeric` | `2-digit` | `long` | `short` | `narrow`
- `year` - `numeric` | `2-digit`
- `era` - `long` | `short` | `narrow`
- `timeStyle` - `full` | `long` | `medium` | `short`
- `hour` - `numeric` | `2-digit`
- `minute` - `numeric` | `2-digit`
- `second` - `numeric` | `2-digit`
- `dayPeriod` - `narrow` | `short` | `long`
- `timeZoneName` - `long` | `short`

_Default options_

```js
{
  locale: [],
  date: new Date(),
  dateStyle: 'short'
}
```

_Example_

```js
import { formatDateTime } from 'easy-intl'

console.log(
  '\n',
  // my default locale is `ru-RU`
  formatDateTime(), // 17.08.2021
  '\n',
  formatDateTime({ locale: 'en-US', dateStyle: 'short', timeStyle: 'short' }), // 8/17/21, 3:57 PM,
  '\n',
  formatDateTime({ locale: 'en-GB', dateStyle: 'long', timeStyle: 'short' }), // 17 August 2021 at 15:57
  '\n',
  formatDateTime({ locale: 'ja-JP', dateStyle: 'short' }), // 2021/08/17
  '\n',
  formatDateTime({ locale: 'es-ES', dateStyle: 'full', timeStyle: 'full' }), // martes, 17 de agosto de 2021, 15:57:49 (hora estándar de Ekaterimburgo)
  '\n',
  formatDateTime({
    locale: 'fr-FR',
    weekday: 'long',
    day: '2-digit',
    month: 'long',
    year: 'numeric',
    hour: '2-digit'
  }) // mardi 17 août 2021, 15 h
)
```

### `formatRelativeTime`

__Util for [`Intl.RelativeTimeFormat`](https://tc39.es/ecma402/#relativetimeformat-objects)__

_Signature_

```js
formatRelativeTime(options) // intl.relative(value, options)
```

_Options_

- `locale` - language | country code
- `value` - `string` with `amount` and `unit` (may be separated by space or underscore), e.g. `1 week`, `-2 day`, `3_month` etc.
- `numeric` - `always` e.g. `1 day ago` | `auto` e.g. `yesterday`
- `style` - `long` | `short` | `narrow`

_Default options_

```js
{
  locale: [],
  value: '1 day',
  numeric: 'always'
}
```

_Example_

```js
import { formatRelativeTime } from 'easy-intl'

console.log(
  '\n',
  // default value is `1 day`
  // my default locale is `ru-RU`
  formatRelativeTime(), // через 1 день
  '\n',
  formatRelativeTime({ locale: 'en-US', value: '-1 day', numeric: 'auto' }), // yesterday
  '\n',
  formatRelativeTime({
    locale: 'fr-FR',
    value: '1_week',
    style: 'long'
  }), // dans 1 semaine
  '\n',
  formatRelativeTime({
    locale: 'ja-JP',
    value: '-1_month',
    numeric: 'auto',
    style: 'long'
  }) // 先月
)
```

### `formatNumber`

__Util for [`Intl.NumberFormat`](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Intl/NumberFormat)__

_Signature_

```js
formatNumber(options) // intl.number(number, options)
```

_Options_

- `locale` - language | country code
- `number`
- `style` - `decimal` | `currency` | `percent` | `unit`. Other options depends on this value
- `notation` - `standard` | `scientific` | `engineering` | `compact`
- `numberingSystem` - `arab`, `beng`, `deva`, `fullwide`, `latn` etc.
- `minimumIntegerDigits` - `1` by default
- `minimumFractionDigits` - `0` by default
- `maximumFractionDigits` - highest number of `minimumFractionDigits` and `3`
- `minimumSignificantDigits` - `1` by default
- `maximumSignificantDigits` - `minimumSignificantDigits` by default
- `signDisplay` - `auto` | `never` | `always` | `exceptZero`
- `useGrouping: boolean` - if `false`, thousands separators will be ignored
- `compactDisplay` - formatting style when `notation: compact`
- `currency` - `USD`, `EUR`, `RUB` etc. (when `style: currency`)
- `currencyDisplay` - displaying sign or name of currency when `style: currency`
- `currencySign`: `standard` | `accounting` (when `style: currency`)
- `unit`: `centimeter`, `meter`, `minute`, `hour`, `byte` etc.
- `unitDisplay`: `long` | `short` | `narrow` (when `style: unit`)

_Default options_

```js
{
  locale: [],
  number: 0,
  style: 'decimal'
}
```

_Example_

```js
import { formatNumber } from 'easy-intl'

console.log(
  '\n',
  // my default locale is `ru-RU`
  formatNumber({ number: 1234.56 }),
  '\n', // 1 234,56
  formatNumber({ locale: 'en-US', number: 1234.56 }),
  '\n', // 1,234.56
  formatNumber({ locale: 'de-DE', number: 1234.56, style: 'currency', currency: 'EUR' }),
  '\n', // 1.234,56 €
  formatNumber({ locale: 'fr-FR', number: 1234.56, style: 'percent' }),
  '\n', // 123 456 %
  formatNumber({
    locale: 'it-IT',
    number: 1234.56,
    style: 'unit',
    unit: 'celsius',
    minimumFractionDigits: 3
  }),
  '\n' // 1.234,560 °C
)
```

### `formatNames`

__Util for [`Intl.DisplayNames`](https://tc39.es/ecma402/#intl-displaynames-objects)__

_Signature_

```js
formatNames(options) // intl.names(localeOf, options)
```

_Options_

- `locale` - language | country code
- `localeOf` - language | country code
- `type` -  `language`, `region`, `script`, `currency`
- `style` - `long` | `short` | `narrow`
- `fallback` - `code` | `none`

_Default options_

```js
{
  locale: [],
  localeOf: 'en-US',
  type: 'language'
}
```

_Example_

```js
import { formatNames } from 'easy-intl'

console.log(
  '\n',
  // my default locale is `ru-RU`
  formatName(),
  '\n', // американский английский
  formatName({
    localeOf: 'Egyp',
    type: 'script'
  }),
  '\n', // египетская иероглифическая
  formatName({
    locale: 'fr-FR',
    localeOf: 'AU',
    type: 'region'
  }),
  '\n', // Australie
  formatName({
    locale: 'pl-PL',
    localeOf: 'GBP',
    type: 'currency',
    style: 'long'
  }),
  '\n' // funt szterling
)
```

### `formatList`

__Util for [`Intl.ListFormat`](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Intl/ListFormat)__

_Signature_

```js
formatList(options) // intl.list(list, options)
```

_Options_

- `locale` - language | country code
- `list` - `array` | `string`
- `type` - `conjunction` (and) | `disjunction` (or) | `unit`
- `style` - `long` | `short` | `narrow`

_Default options_

```js
{
  locale: [],
  list: [],
  type: 'conjunction'
}
```

_Example_

```js
import { formatList } from 'easy-intl'

const list = ['Chrome', 'Firefox', 'Safari']

console.log(
  '\n',
  // my default locale is `ru-RU`
  formatList({ list }),
  '\n', // Chrome, Firefox и Safari
  formatList({ locale: 'en-US', list, style: 'short' }),
  '\n', // Chrome, Firefox, & Safari
  formatList({ locale: 'ja-JP', list, type: 'disjunction' }),
  '\n' // Chrome、Firefox、またはSafari
)
```

### `compareValues`

__Util for [`Intl.Collator`](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Intl/Collator)__

_Signature_

```js
compareValues(options) // intl.compare(values, options)
```

_Options_

- `locale` - language | country code
- `values` - `array` | `string`
- `usage` - `sort` | `search`
- `sensitivity` - `base` | `accent` | `case` | `variant`
- `collation` - `big5han` | `dict` | `direct` | `ducet` etc.
- `numeric` - `boolean`
- `ignorePunctuation` - `boolean`
- `caseFirst` - `upper` | `lower` | `false`

_Default options_

```js
{
  locale: [],
  values: [],
  usage: 'sort'
}
```

_Example_

```js
import { compareValues } from 'easy-intl'

console.log(
  '\n',
  compareValues({ values: ['a', 'á'], sensitivity: 'base' }),
  '\n', // 0 -> equal
  compareValues({ values: ['2', '10'] }),
  '\n', // 1 -> '2' > '10'
  compareValues({ values: ['2', '10'], numeric: true }),
  '\n' // -1 -> 2 < 10
)
```

### `pluralize`

__Util for [`Intl.PluralRules`](https://tc39.es/ecma402/#pluralrules-objects)__

Currently only `en-US` locale is supported.

_Signature_

```js
pluralize(options) // intl.plural(number, options)
```

_Options_

- `locale` - language | country code
- `number`
- `type` - `cardinal` | `ordinal`

_Default options_

```js
{
  locale: [],
  number: 0,
  type: 'cardinal'
}
```

_Example_

```js
import { pluralize } from 'easy-intl'

console.log(
  '\n',
  pluralize(),
  '\n', // one
  // only `en-US` locale supported
  pluralize({ locale: 'ru-RU', type: 'ordinal' }),
  '\n' // other
)
```