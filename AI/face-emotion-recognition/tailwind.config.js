module.exports = {
  content: ['./src/components/**/*.{js,jsx,ts,tsx}'],
  theme: {
    extend: {
      colors: {
        bg: {
          1: '#1A1615',
          2: '#f2f6f9',
        },
        fg: {
          1: '#f2f6f9',
          2: '#FF5C58',
        },
      },
      fontFamily: {
        dmMono: ['DM Mono', 'monospace'],
      },
    },
  },
  plugins: [],
};
