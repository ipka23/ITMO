import {
  formatDateTime,
  formatRelativeTime,
  formatNumber,
  formatNames,
  formatList,
  compareValues,
  pluralize
} from './utils.js'

export class EasyIntl {
  #defaultLocale = []
  #defaultRoot = document.body
  elements = []
  map = {}

  constructor(options = {}) {
    this.dictionary = options.dictionary || {}
    this.options = Object.entries(options).reduce((a, [k, v]) => {
      if (this[k] && k !== 'locale' && k !== 'root' && k !== 'dictionary') {
        a[k] = v
      }
      return a
    }, {})
    this.autorun = options.autorun ?? true

    if (options.root) {
      this.root = options.root
    } else {
      this._getElements()
    }

    if (options.locale) {
      this.locale = options.locale
    }

    if (this.autorun && !options.locale) {
      this.localize()
    }
  }

  set locale(value) {
    this.#defaultLocale = value

    if (this.autorun) {
      this.localize()
    }
  }

  set root(value) {
    if (typeof value === 'string') {
      this.#defaultRoot = document.querySelector(value)
    } else {
      this.#defaultRoot = value
    }

    this._getElements()
  }

  _getElements() {
    const intlElements = [
      ...this.#defaultRoot.querySelectorAll('[data-intl_type]')
    ].filter((el) => {
      const closestIntlRoot = el.closest('[data-intl_root]')
      return !(closestIntlRoot && closestIntlRoot !== this.#defaultRoot)
    })

    this.elements = intlElements
  }

  date(date, options = this.options.date || {}) {
    return formatDateTime({
      locale: options.locale || this.#defaultLocale,
      date,
      ...options
    })
  }

  relative(value, options = this.options.relative || {}) {
    return formatRelativeTime({
      locale: options.locale || this.#defaultLocale,
      value,
      ...options
    })
  }

  number(number, options = this.options.number || {}) {
    return formatNumber({
      locale: options.locale || this.#defaultLocale,
      number,
      ...options
    })
  }

  names(localeOf, options = this.options.names || {}) {
    return formatNames({
      locale: options.locale || this.#defaultLocale,
      localeOf,
      options
    })
  }

  list(list, options = this.options.list || {}) {
    return formatList({
      locale: options.locale || this.#defaultLocale,
      list,
      ...options
    })
  }

  compare(values, options = this.options.compare || {}) {
    return compareValues({
      locale: options.locale || this.#defaultLocale,
      values,
      ...options
    })
  }

  plural(number, options = this.options.plural || {}) {
    return pluralize({
      locale: options.locale || this.#defaultLocale,
      number,
      ...options
    })
  }

  custom(value) {
    return this.dictionary[this.#defaultLocale] &&
      this.dictionary[this.#defaultLocale][value]
      ? this.dictionary[this.#defaultLocale][value]
      : null
  }

  localize(options = {}) {
    if (!'Intl' in window) {
      return console.error('Internalization API not supported in your browser')
    }

    if (options.locale) {
      this.locale = options.locale
    }

    if (options.root) {
      this.root = options.root
    }

    this.elements.forEach((el) => {
      const { intl_type, intl_value, intl_options, intl_map } = el.dataset

      let _options = { ...this.options[intl_type] }

      if (Object.keys(options).length > 0) {
        _options = { ..._options, ...options[intl_type] }
      }

      if (intl_options) {
        intl_options
          .replace(/['"`{}]/g, '')
          .split(/[,;]/)
          .reduce((a, c) => {
            const [k, v] = c.trim().split(':')
            a[k] = v.trim()
            return a
          }, _options)
      }

      const localizedValue = this[intl_type](intl_value, _options)

      if (localizedValue) {
        el.textContent = localizedValue
        if (el.hasAttribute('title')) {
          el.title = localizedValue
        }
        if (el.hasAttribute('placeholder')) {
          el.placeholder = localizedValue
        }
        if (intl_map) {
          this.map[intl_map] = localizedValue
        }
      }
    })
  }
}

export {
  formatDateTime,
  formatRelativeTime,
  formatNumber,
  formatNames,
  formatList,
  compareValues
}
