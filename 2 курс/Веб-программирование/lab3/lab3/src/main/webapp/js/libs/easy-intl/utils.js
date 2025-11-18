export const formatDateTime = ({
  locale = [],
  date = Date.now(),
  ...options
} = {}) => new Intl.DateTimeFormat(locale, options).format(new Date(date))

export const formatRelativeTime = ({
  locale = [],
  value = '1 day',
  ...options
} = {}) => {
  const [amount, unit] = value.split(/[\s_]/)
  return new Intl.RelativeTimeFormat(locale, options).format(amount, unit)
}

export const formatNumber = ({ locale = [], number = 0, ...options } = {}) =>
  new Intl.NumberFormat(locale, options).format(Number(number))

export const formatNames = ({
  locale = [],
  localeOf = 'en-US',
  type = 'language',
  ...options
} = {}) => new Intl.DisplayNames(locale, { type, ...options }).of(localeOf)

export const formatList = ({ locale = [], list = [], ...options } = {}) => {
  if (typeof list === 'string') {
    list = list
      .replace(/[\[\]]/g, '')
      .split(/[,;]/)
      .map((item) => item.replace(/['"`]/g, '').trim())
  }
  return new Intl.ListFormat(locale, options).format(list)
}

export const compareValues = ({ locale = [], values = [], ...options }) => {
  if (typeof values === 'string') {
    values = values
      .replace(/[\[\]]/g, '')
      .split(/[,;]/)
      .map((item) => item.replace(/['"`]/g, '').trim())
  }
  return new Intl.Collator(locale, options).compare(...values)
}

export const pluralize = ({ locale = [], number = 1, ...options } = {}) =>
  new Intl.PluralRules(locale, options).select(number)
