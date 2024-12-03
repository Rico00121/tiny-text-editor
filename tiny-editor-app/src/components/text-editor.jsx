import React, { useState, useEffect } from 'react'
import { ThemeProvider, createTheme } from '@mui/material/styles'
import CssBaseline from '@mui/material/CssBaseline'
import Box from '@mui/material/Box'
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Paper from '@mui/material/Paper'
import Typography from '@mui/material/Typography'
import TextField from '@mui/material/TextField'
import List from '@mui/material/List'
import ListItem from '@mui/material/ListItem'
import ListItemText from '@mui/material/ListItemText'
import { alpha } from '@mui/material/styles'
import {Button, Grid2} from "@mui/material";

// Create a hacker-inspired dark theme
const hackerTheme = createTheme({
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

export default function TextEditor() {
    const [inputText, setInputText] = useState('')
    const [outputText, setOutputText] = useState('')
    const [clipboardContent, setClipboardContent] = useState('')
    const [history, setHistory] = useState([])


    const handleInputChange = (e) => {
        const newText = e.target.value
        setInputText(newText)
        setOutputText(newText)
        setHistory(prev => [`Text updated: ${new Date().toLocaleTimeString()}`, ...prev])
    }

    const handleSelectChange = (e) => {
        console.log('now selected:' + e.target.selectionStart + ','+ e.target.selectionEnd);
    }

    const handleCopy = (e) => {
        console.log("copy happen")
    }

    const handlePaste = (e) => {
        console.log("paste happen")
    }

    const handleCut = (e) => {
        console.log("cut happen")
    }

    const handleKeyDown = (e) => {
        if (e.key === 'Backspace' || e.key === 'Delete') {
            console.log('press delete')
        }
    };

    return (
        <ThemeProvider theme={hackerTheme}>
            <CssBaseline />
            <Container
                sx={{height: '100vh', width: '100vw'}}>
                <Box
                    sx={{
                        display: 'flex',
                        flexDirection: 'column',
                        background: 'linear-gradient(135deg, #000000 0%, #001100 100%)',
                        p: 3,
                    }}
                >
                    <Typography
                        variant="h2"
                        component="h1"
                        gutterBottom
                        align="center"
                        sx={{
                            mb: 4,
                            color: '#00FF00',
                            fontWeight: 'bold',
                            textShadow: '0 0 10px rgba(0, 255, 0, 0.7)',
                        }}
                    >Tiny Text Editor</Typography>
                    {/* Use Grid to make it responsive */}
                    <Grid2 container spacing={3}
                          sx={{ display: 'flex', flexDirection: 'row' }}>
                        <Grid2 container size={6} spacing={3}
                               sx={{ display: 'flex', flexDirection: 'column' }}>
                            <Grid2 item size={12}>
                                <Paper elevation={3} sx={{ p: 2 }}>
                                    <Typography variant="h5" gutterBottom sx={{ color: '#00FF00' }}>
                                        Input
                                    </Typography>
                                    <Box sx={{paddingY: 1}}>
                                        <Button>Start Recording</Button>
                                        <Button>Stop Recording</Button>
                                        <Button>Replay Recording</Button>
                                    </Box>
                                    <TextField
                                        fullWidth
                                        multiline
                                        rows={5}
                                        value={inputText}
                                        onChange={handleInputChange}
                                        onSelect={handleSelectChange}
                                        onCopy={handleCopy}
                                        onCut={handleCut}
                                        onPaste={handlePaste}
                                        onKeyDown={handleKeyDown}
                                        variant="outlined"
                                        sx={{
                                            '& .MuiInputBase-input': {
                                                color: '#00FF00',
                                                fontFamily: '"Fira Code", "Roboto Mono", monospace',
                                            },
                                            '& .MuiOutlinedInput-root': {
                                                backgroundColor: 'rgba(0, 20, 0, 0.3)',
                                            },
                                        }}
                                    />
                                </Paper>
                            </Grid2>
                            <Grid2 item size={12}>
                                <Paper elevation={3} sx={{ display: 'flex', flexDirection: 'column', p: 2 }}>
                                    <Typography variant="h5" gutterBottom sx={{ color: '#00FF00' }}>
                                        Editor State
                                    </Typography>
                                    <Box sx={{ flex: 1, display: 'flex', flexDirection: 'column', gap: 2 }}>
                                        <Paper
                                            variant="outlined"
                                            sx={{
                                                flex: 1,
                                                p: 2,
                                                overflowY: 'auto',
                                                fontFamily: '"Fira Code", "Roboto Mono", monospace',
                                                fontSize: '0.9rem',
                                                color: '#00FF00',
                                                backgroundColor: alpha('#000000', 0.5),
                                            }}
                                        >
                                            <Typography variant="subtitle2" gutterBottom sx={{ color: '#00FF00' }}>
                                                Current Content:
                                            </Typography>
                                            {outputText || 'No content'}
                                        </Paper>
                                        <Paper
                                            variant="outlined"
                                            sx={{
                                                p: 2,
                                                fontFamily: '"Fira Code", "Roboto Mono", monospace',
                                                fontSize: '0.9rem',
                                                color: '#00FF00',
                                                backgroundColor: alpha('#000000', 0.5),
                                            }}
                                        >
                                            <Typography variant="subtitle2" gutterBottom sx={{ color: '#00FF00' }}>
                                                Clipboard Content:
                                            </Typography>
                                            {clipboardContent || 'Empty'}
                                        </Paper>

                                    </Box>
                                </Paper>
                            </Grid2>
                        </Grid2>
                        <Grid2 item size={6}>
                            <Paper elevation={3} sx={{ height: '100%', display: 'flex', flexDirection: 'column', p: 2 }}>
                                <Typography variant="h5" gutterBottom sx={{ color: '#00FF00' }}>
                                    Editor History
                                </Typography>

                                <Paper
                                    variant="outlined"
                                    sx={{
                                        flex: 1,
                                        p: 2,
                                        overflowY: 'auto',
                                        backgroundColor: alpha('#000000', 0.5),
                                    }}
                                >
                                    <List dense>
                                        {history.map((event, index) => (
                                            <ListItem key={index} sx={{ py: 0.5 }}>
                                                <ListItemText
                                                    primary={event}
                                                    sx={{
                                                        '& .MuiListItemText-primary': {
                                                            color: '#00FF00',
                                                            fontFamily: '"Fira Code", "Roboto Mono", monospace',
                                                            fontSize: '0.8rem',
                                                        },
                                                    }}
                                                />
                                            </ListItem>
                                        ))}
                                    </List>
                                </Paper>
                            </Paper>
                        </Grid2>
                    </Grid2>
                </Box>
            </Container>
        </ThemeProvider>
    )
}


