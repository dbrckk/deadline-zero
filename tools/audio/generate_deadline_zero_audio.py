#!/usr/bin/env python3
"""Generate the original Deadline: Zero combat one-shot pack.

No third-party samples are used. Deterministic seed: 20261004.
Requires numpy, scipy, and ffmpeg/libvorbis.
"""
import subprocess, wave
from pathlib import Path
import numpy as np
from scipy import signal

SR=44100
RNG=np.random.default_rng(20261004)
OUT=Path(__file__).resolve().parents[2]/"godot"/"assets"/"audio"/"authored"
OUT.mkdir(parents=True,exist_ok=True)

def env(n,a=.005,d=.2,p=2.0):
    t=np.arange(n)/SR
    return np.minimum(1,t/max(a,1e-5))*np.maximum(0,1-t/max(d,1e-5))**p

def chirp(f0,f1,n):
    t=np.arange(n)/SR
    return signal.chirp(t,f0=f0,t1=max(t[-1],1/SR),f1=f1,method="logarithmic")

def filt(x,lo=None,hi=None):
    ny=SR/2
    if lo and hi: sos=signal.butter(4,[lo/ny,hi/ny],btype="band",output="sos")
    elif lo: sos=signal.butter(4,lo/ny,btype="high",output="sos")
    elif hi: sos=signal.butter(4,hi/ny,btype="low",output="sos")
    else: return x
    return signal.sosfilt(sos,x)

def verb(x,amount=.18,decay=.5):
    y=x.copy()
    for i,d in enumerate([.031,.047,.071,.113]):
        k=int(d*SR)
        y[k:]+=x[:-k]*amount*(decay**i)
    return y

def save(name,x):
    x=np.tanh(x*1.15)
    x=x/(np.max(np.abs(x))+1e-9)*.82
    wav=OUT/(name+".wav"); ogg=OUT/(name+".ogg")
    pcm=(np.clip(x,-1,1)*32767).astype("<i2")
    with wave.open(str(wav),"wb") as f:
        f.setnchannels(1); f.setsampwidth(2); f.setframerate(SR); f.writeframes(pcm.tobytes())
    subprocess.run(["ffmpeg","-hide_banner","-loglevel","error","-y","-i",str(wav),"-c:a","libvorbis","-q:a","5",str(ogg)],check=True)
    wav.unlink()

def weapon(kind):
    sec,f0,f1,nlo,nhi,ng,sg={
        "vanguard":(.24,235,92,1600,9000,.55,.65),
        "scatter":(.34,150,52,500,5200,.72,.80),
        "rail":(.42,1900,220,3000,13000,.62,.90),
        "inferno":(.38,760,180,180,8500,.46,.84),
        "cryo":(.31,1540,520,4500,15000,.48,.48),
        "arc":(.29,2800,520,1800,12000,.40,.46),
    }[kind]
    n=int(sec*SR)
    tone=chirp(f0,f1,n)*env(n,.0008,sec*.82,2.1)*.52
    sub=chirp(max(58,f0*.26),max(34,f1*.35),n)*env(n,.001,sec*.72,1.9)*sg
    noise=filt(RNG.normal(0,1,n),nlo,nhi)*env(n,.0003,min(sec*.38,.10),4)*ng
    return verb(tone+sub+noise,.15,.52)

def impact(kind):
    sec,f0,f1,cg,sg={
        "hit":(.18,120,64,.35,.25),
        "critical":(.28,155,52,.58,.46),
        "kill":(.42,105,38,.68,.60),
        "boss":(.58,82,28,.80,.76),
    }[kind]
    n=int(sec*SR)
    metal=chirp(920,180,n)*env(n,.0005,sec*.8,2.4)*.34
    sub=chirp(f0,f1,n)*env(n,.0007,sec*.86,1.8)*sg
    crack=filt(RNG.normal(0,1,n),900,10000)*env(n,.0002,sec*.22,4)*cg
    tail=filt(RNG.normal(0,1,n),140,1800)*env(n,.005,sec*.9,2.6)*.18
    return verb(metal+sub+crack+tail,.18 if kind=="hit" else .26,.58)

for n in ["vanguard","scatter","rail","inferno","cryo","arc"]:
    save("weapon_"+n,weapon(n))
for n in ["hit","critical","kill","boss"]:
    save("impact_"+n,impact(n))
print("Generated 10 original Deadline: Zero OGG assets in",OUT)
