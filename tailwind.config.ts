import type { Config } from 'tailwindcss';

const config: Config = {
  content: [
    './src/pages/**/*.{js,ts,jsx,tsx,mdx}',
    './src/components/**/*.{js,ts,jsx,tsx,mdx}',
    './src/app/**/*.{js,ts,jsx,tsx,mdx}',
  ],
  darkMode: 'class',
  theme: {
    extend: {
      colors: {
        primary: '#296FCF',
        'primary-bg': '#B9D2F4',
        content: {
          light: '#191f28',
          dark: '#FFFFFF',
        },
        'content-secondary': {
          light: '#4e5968',
          dark: '#C9D1DD',
        },
        background: {
          light: '#FFFFFF',
          dark: '#2B2B2B',
        },
        divider: {
          light: '#E0E1E3',
          dark: '#6C6E6F',
        },
        tag: {
          text: '#191f28',
          bg: '#f2f4f6',
        },
      },
      fontFamily: {
        pretendard: ['Pretendard', 'sans-serif'],
      },
      maxWidth: {
        'content': '70rem',
      },
    },
  },
  plugins: [],
};

export default config;
