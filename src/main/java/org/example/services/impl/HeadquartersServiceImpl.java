package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.entities.BotUser;
import org.example.entities.Headquarters;
import org.example.entities.Role;
import org.example.entities.Volunteer;
import org.example.enums.ERole;
import org.example.exceptions.HeadquartersNotFoundException;
import org.example.exceptions.PermissionDeniedException;
import org.example.mapper.HeadquartersMapper;
import org.example.pojo.dto.card.HeadquartersCardDto;
import org.example.pojo.dto.create.HeadquartersCreateDto;
import org.example.pojo.dto.table.HeadquartersTableDto;
import org.example.pojo.dto.update.HeadquartersUpdateDto;
import org.example.repositories.HeadquartersRepository;
import org.example.repositories.UserRepository;
import org.example.repositories.VolunteerRepository;
import org.example.services.HeadquartersService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HeadquartersServiceImpl implements HeadquartersService {
    private final HeadquartersRepository headquartersRepository;
    private final HeadquartersMapper headquartersMapper;
    private final UserRepository userRepository;
    private final VolunteerRepository volunteerRepository;

    @Override
    public List<HeadquartersTableDto> getHeadquartersList() {
        /*BotUser botUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found by username: " + SecurityContextHolder.getContext().getAuthentication().getName()));
        if (botUser.getRoleList().stream()
                .map(Role::getRoleName)
                .noneMatch(role -> role == ERole.ROLE_REGIONAL_HEAD || role == ERole.ROLE_REGIONAL_TEAM_HEAD)) {
            throw new PermissionDeniedException();
        }*/
        return headquartersRepository.findAll().stream().map(headquartersMapper::headquartersDto).toList();
    }

    @Override
    public long addHeadquarters(HeadquartersCreateDto headquartersCreateDto) {
        /*BotUser botUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found by username: " + SecurityContextHolder.getContext().getAuthentication().getName()));
        if (botUser.getRoleList().stream()
                .map(Role::getRoleName)
                .noneMatch(role -> role == ERole.ROLE_REGIONAL_HEAD || role == ERole.ROLE_REGIONAL_TEAM_HEAD)) {
            throw new PermissionDeniedException();
        }*/
        return headquartersRepository.saveAndFlush(headquartersMapper.headquarters(headquartersCreateDto)).getId();
    }

    @Override
    public long deleteHeadquarters(Long id) {
        /*BotUser botUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found by username: " + SecurityContextHolder.getContext().getAuthentication().getName()));
        if (botUser.getRoleList().stream()
                .map(Role::getRoleName)
                .noneMatch(role -> role == ERole.ROLE_REGIONAL_HEAD || role == ERole.ROLE_REGIONAL_TEAM_HEAD)) {
            throw new PermissionDeniedException();
        }*/
        headquartersRepository.deleteById(id);
        return id;
    }

    @Override
    public HeadquartersCardDto getHeadquartersCard(Long id) {
        /*Volunteer volunteer = volunteerRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found by username: " + SecurityContextHolder.getContext().getAuthentication().getName()));
        BotUser botUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found by username: " + SecurityContextHolder.getContext().getAuthentication().getName()));
        if (botUser.getRoleList().stream()
                .map(Role::getRoleName)
                .noneMatch(role -> role == ERole.ROLE_REGIONAL_HEAD || role == ERole.ROLE_REGIONAL_TEAM_HEAD) && !volunteer.getHeadquarters().getId().equals(id)) {
            throw new PermissionDeniedException();
        }*/
        return headquartersRepository.findById(id).map(headquartersMapper::headquartersCardDto)
                .orElseThrow(() -> new HeadquartersNotFoundException(id.toString()));
    }

    @Override
    public void update(List<HeadquartersUpdateDto> dtoList) {
        /*BotUser botUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found by username: " + SecurityContextHolder.getContext().getAuthentication().getName()));
        if (botUser.getRoleList().stream()
                .map(Role::getRoleName)
                .noneMatch(role -> role == ERole.ROLE_REGIONAL_HEAD || role == ERole.ROLE_REGIONAL_TEAM_HEAD)) {
            throw new PermissionDeniedException();
        }*/
        dtoList.forEach(dto -> {
                    Headquarters headquarters = headquartersRepository.findById(dto.getId()).orElseThrow(() -> new HeadquartersNotFoundException(dto.getId().toString()));
                    headquartersRepository.saveAndFlush(headquartersMapper.headquarters(headquarters, dto));
                }
        );
    }
}
