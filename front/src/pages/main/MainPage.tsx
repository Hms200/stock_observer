import Input from '../../components/common/form/Input.tsx'

const MainPage = () => {
    return (
        <div className={'w-1/2 h-1/2 border'}>
            <Input type={'text'} label={'test'} required placeholder={'필수'} />
        </div>
    )
}

export default MainPage
