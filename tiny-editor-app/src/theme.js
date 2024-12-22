import {alpha, createTheme} from "@mui/material/styles";

export const hackerTheme = createTheme({
    palette: {
        mode: 'dark',
        primary: {
            main: '#00FF00',
        },
        background: {
            default: '#000000',
            paper: '#0A0A0A',
        },
        text: {
            primary: '#00FF00',
            secondary: '#008F00',
        },
    },
    typography: {
        fontFamily: '"Fira Code", "Roboto Mono", monospace',
    },
    components: {
        MuiPaper: {
            styleOverrides: {
                root: {
                    backgroundImage: 'none',
                    backgroundColor: alpha('#0A0A0A', 0.8),
                    backdropFilter: 'blur(10px)',
                    border: '1px solid #00FF00',
                    boxShadow: '0 0 10px rgba(0, 255, 0, 0.1)',
                },
            },
        },
        MuiTextField: {
            styleOverrides: {
                root: {
                    '& .MuiOutlinedInput-root': {
                        '& fieldset': {
                            borderColor: 'rgba(0, 255, 0, 0.5)',
                        },
                        '&:hover fieldset': {
                            borderColor: 'rgba(0, 255, 0, 0.7)',
                        },
                        '&.Mui-focused fieldset': {
                            borderColor: '#00FF00',
                        },
                    },
                },
            },
        },
    },
})