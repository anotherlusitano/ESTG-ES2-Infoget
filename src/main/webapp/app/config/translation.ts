import { Storage, TranslatorContext } from 'react-jhipster';

import { setLocale } from 'app/shared/reducers/locale';

TranslatorContext.setDefaultLocale('pt-pt');
TranslatorContext.setRenderInnerTextForMissingKeys(false);

export const languages: any = {
  'pt-pt': { name: 'Português' },
  // jhipster-needle-i18n-language-key-pipe - JHipster will add/remove languages in this object
};

export const locales = Object.keys(languages).sort();

export const registerLocale = store => {
  store.dispatch(setLocale(Storage.session.get('locale', 'pt-pt')));
};
